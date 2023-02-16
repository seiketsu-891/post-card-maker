package com.louie.coding.service;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.louie.coding.constants.MailConstants;
import com.louie.coding.dao.UserDao;
import com.louie.coding.entity.RefreshTokenDetail;
import com.louie.coding.entity.User;
import com.louie.coding.entityReq.UserLogin;
import com.louie.coding.entityReq.UserRegister;
import com.louie.coding.entityReq.UserRestPassword;
import com.louie.coding.exception.AuthException;
import com.louie.coding.exception.AuthExceptionCode;
import com.louie.coding.exception.BusinessException;
import com.louie.coding.exception.BusinessExceptionCode;
import com.louie.coding.util.CopyUtil;
import com.louie.coding.util.MD5Util;
import com.louie.coding.util.RSAUtil;
import com.louie.coding.util.TokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
public class UserService {
    private static Logger LOGGER = LoggerFactory.getLogger(UserService.class);
    @Resource
    private UserDao userDao;
    @Resource
    private RedisService redisService;
    @Resource
    private MailService mailService;

    public void addUser(UserRegister userRegister) {
        // 这里要不要再次验证邮箱唯一性？
        // 问题可以转换成：是否存在情况：用户发送验证码到提交前，在别处又用这个邮箱注册成功了？
        // 假设用户打开一个浏览器a，发送了验证码，
        // 此时又打开另一个浏览器b，发送了验证码，会覆盖掉之前的验证码
        // 此时用户在浏览器a输入这个验证码注册成功，这个验证码会失效，b会无法使用这个验证码提交
        // 所以这里应该可以不验证邮箱唯一性

        // 核对验证码
        String code = userRegister.getCode();
        String redisKey = MailConstants.REDIS_KEY_MAIL_CODE_PREFIX + userRegister.getEmail();
        String codeDb = redisService.getValue(redisKey);
        if (codeDb == null || !codeDb.equals(code)) {
            throw new BusinessException(BusinessExceptionCode.WRONG_VERIFICATION_CODE);
        }
        redisService.deleteKey(redisKey);

        User user = CopyUtil.copy(userRegister, User.class);
        Date now = new Date();
        String salt = String.valueOf(now.getTime());
        String MD5Password = getMD5Password(user.getPassword(), salt);
        user.setCreateTime(now);
        user.setPassword(MD5Password);
        /*
           这里需要将salt作为一个user表的字段存储起来
           因为如果不存储，而是login时直接通过createTime再次用getTime()方法的话，
           由于数据库中的时间数据精度和这里的时间精度不同，会造成结果不一样。
         */
        user.setSalt(salt);
        user.setIsPremium(false);
        userDao.addUser(user);
    }

    public Map<String, Object> login(UserLogin user) {
        String email = user.getEmail();
        User userDb = userDao.getUserByEmail(email);
        if (userDb == null) {
            throw new BusinessException(BusinessExceptionCode.LOGIN_ERROR);
        }
        String salt = userDb.getSalt();
        String MD5Password = getMD5Password(user.getPassword(), salt);
        if (!MD5Password.equals(userDb.getPassword())) {
            throw new BusinessException(BusinessExceptionCode.LOGIN_ERROR);
        }

        // 生成双token
        Map<String, Object> tokens = new HashMap<>();
        // 这里需要用数据库的user信息来生成token
        String token = TokenUtil.generateToken(userDb, "0");
        String refreshToken = TokenUtil.generateToken(userDb, "1");
        tokens.put("token", token);
        tokens.put("refreshToken", refreshToken);

        // 将refreshToken存入数据库
        Long userId = userDb.getId();
        RefreshTokenDetail refreshTokenDetail = new RefreshTokenDetail();
        refreshTokenDetail.setUserId(userId);
        refreshTokenDetail.setToken(refreshToken);
        refreshTokenDetail.setCreateTime(new Date());
        userDao.deleteRefreshToken(refreshToken);
        userDao.addRefreshToken(refreshTokenDetail);

        return tokens;
    }

    /**
     * 将前端传来的密码先解密，然后再用MD5进行加密
     */
    private String getMD5Password(String pwd, String salt) {
        String passwordRaw;
        try {
            passwordRaw = RSAUtil.decrypt(pwd);
        } catch (Exception e) {
            throw new BusinessException(BusinessExceptionCode.ERROR_PASSWORD_DECRYPTION);
        }
        return MD5Util.sign(passwordRaw, String.valueOf(salt), "UTF-8");
    }

    public void sendVerificationCodeByMail(String email, int purpose) {
        User userDb = userDao.getUserByEmail(email);

        // 注册用 - 需要判定邮箱是否已存在
        if (MailConstants.CODE_PURPOSE_REGISTER == purpose) {
            if (userDb != null) {
                throw new BusinessException(BusinessExceptionCode.USER_EMAIL_EXISTS);
            }
        } else { // 非注册用需要邮箱已注册
            if (userDb == null) {
                throw new BusinessException(BusinessExceptionCode.USER_EMAIL_NOT_EXISTS);
            }
        }

        String redisKey = MailConstants.REDIS_KEY_MAIL_CODE_PREFIX + email;
        // 检查用户1分钟之内是否已经发送了邮件
        Long leftValidTimeInSeconds = redisService.getLeftValidTimeInSeconds(redisKey);
        LOGGER.info("验证码剩余有效期是:{}分", leftValidTimeInSeconds / 60);
        // 判断剩余有效期是否大于： 总有效期-限定的发送频率
        if (leftValidTimeInSeconds > MailConstants.MAIL_CODE_VALID_TIME - MailConstants.MAIL_CODE_RESEND_MIN_INTERVAL) {
            throw new BusinessException(BusinessExceptionCode.MAIL_SEND_TOO_OFTEN);
        }
        // 生成验证码并发送邮件;
        String code = mailService.sendEmail(email);
        // 将验证码存入redis
        redisService.setValue(redisKey, code, MailConstants.MAIL_CODE_VALID_TIME);
    }

    public Boolean checkIfPremium(Long userId) {
        return userDao.getIsPremiumByUserId(userId);
    }

    public String refreshToken(String refreshToken) {
        try {
            TokenUtil.verifyToken(refreshToken);
        } catch (TokenExpiredException e) {
            userDao.deleteRefreshToken(refreshToken);
            throw new AuthException(AuthExceptionCode.TOKEN_EXPIRED);
        }

        RefreshTokenDetail refreshTokenDetail = userDao.getRefreshTokenDetail(refreshToken);
        if (refreshTokenDetail == null) {
            throw new AuthException(AuthExceptionCode.TOKEN_ILLEGAL);
        }

        return TokenUtil.refreshToken(refreshTokenDetail.getUserId());
    }

    public void resetPassword(UserRestPassword userRestPassword) {
        String email = userRestPassword.getEmail();
        User userDb = userDao.getUserByEmail(email);
        if (userDb == null) {
            throw new BusinessException(BusinessExceptionCode.USER_EMAIL_NOT_EXISTS);
        }

        //校验验证码
        String code = userRestPassword.getCode();
        String redisKey = MailConstants.REDIS_KEY_MAIL_CODE_PREFIX + userRestPassword.getEmail();
        String codeDb = redisService.getValue(redisKey);
        if (codeDb == null || !codeDb.equals(code)) {
            throw new BusinessException(BusinessExceptionCode.WRONG_VERIFICATION_CODE);
        }
        redisService.deleteKey(redisKey);

        String salt = userDb.getSalt();
        String MD5Password = getMD5Password(userRestPassword.getPassword(), salt);
        User user = new User();
        user.setId(userDb.getId());
        user.setPassword(MD5Password);
        user.setUpdateTime(new Date());
        System.out.println(user);
        userDao.updatePassword(user);
    }
}
