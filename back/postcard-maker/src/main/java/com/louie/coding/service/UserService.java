package com.louie.coding.service;

import com.louie.coding.constants.MailConstants;
import com.louie.coding.dao.UserDao;
import com.louie.coding.entity.User;
import com.louie.coding.exception.BusinessException;
import com.louie.coding.exception.BusinessExceptionCode;
import com.louie.coding.util.MD5Util;
import com.louie.coding.util.RSAUtil;
import com.louie.coding.util.TokenUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Date;

@Service
public class UserService {
    @Value("${spring.mail.username}")
    String MAIL_FROM_ADDRESS;
    @Resource
    private UserDao userDao;
    @Resource
    private JavaMailSender mailSender;

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
        /*
           邮箱验证一般有两种类型，一种是点击链接，一种是发送验证码，这里是发送验证码
           如果是点击链接，则需要生成生成一个链接，链接内可以添加一个设置了有效期的token
           同时还需要添加链接
         */
        String content = "您的验证码是123";
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        try {
            helper.setFrom(MAIL_FROM_ADDRESS, MailConstants.SENDER_NAME);
            helper.setTo(email);
            helper.setSubject(MailConstants.EMAIL_SUBJECT);
            helper.setText(content, true);
            mailSender.send(message);
        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new BusinessException(BusinessExceptionCode.ERROR_SENDING_MAIL);
        }
    }
}
