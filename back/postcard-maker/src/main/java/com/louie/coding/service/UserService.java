package com.louie.coding.service;

import com.louie.coding.constants.MailConstants;
import com.louie.coding.dao.UserDao;
import com.louie.coding.entity.User;
import com.louie.coding.exception.BusinessException;
import com.louie.coding.exception.BusinessExceptionCode;
import com.louie.coding.util.MD5Util;
import com.louie.coding.util.RSAUtil;
import com.louie.coding.util.TokenUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class UserService {
    @Resource
    private UserDao userDao;
    @Resource
    private RedisService redisService;
    @Resource
    private MailService mailService;

    public String addUser(User user) {
        String email = user.getEmail();
        User userDb = userDao.getUserByEmail(email);
        if (userDb != null) {
            throw new BusinessException(BusinessExceptionCode.USER_EMAIL_EXISTS);
        }

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
        userDao.addUser(user);
        return TokenUtil.generateToken(user);
    }

    public String login(User user) {
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
        return TokenUtil.generateToken(user);
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

    public void sendVerificationCodeByMail(String email) {
        String redisKey = MailConstants.REDIS_KEY_MAIL_CODE_PREFIX + email;
        // 检查用户1分钟之内是否已经发送了邮件
        Long leftValidTimeInSeconds = redisService.getLeftValidTimeInSeconds(redisKey);
        if (leftValidTimeInSeconds > MailConstants.MAIL_CODE_VALID_TIME - MailConstants.MAIL_CODE_RESEND_MIN_INTERVAL) {
            throw new BusinessException(BusinessExceptionCode.MAIL_SEND_TOO_OFTEN);
        }
        // 生成验证码并发送邮件;
        String code = mailService.sendEmail(email);
        // 将验证码存入redis
        redisService.setValue(redisKey, code, MailConstants.MAIL_CODE_VALID_TIME);
    }
}
