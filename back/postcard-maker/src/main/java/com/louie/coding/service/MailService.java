package com.louie.coding.service;

import com.louie.coding.constants.MailConstants;
import com.louie.coding.exception.BusinessException;
import com.louie.coding.exception.BusinessExceptionCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;

@Service
public class MailService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);
    @Value("${MAIL_USERNAME}")
    String MAIL_FROM_ADDRESS;
    @Resource
    private SpringTemplateEngine templateEngine;
    @Resource
    private JavaMailSender mailSender;

    public String sendEmail(String email) {
         /*
           邮箱验证一般有两种类型，一种是点击链接，一种是发送验证码，这里是发送验证码
           如果是点击链接，则需要生成生成一个链接，链接内可以添加一个设置了有效期的token
           同时还需要添加链接
         */
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            code.append((int) (Math.random() * 10));
        }
        LOGGER.info("邮件验证码是:{}", code);

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        Context context = new Context();
        String name = email.split("@")[0];
        context.setVariable("name", name);
        context.setVariable("code", code);
        String template = templateEngine.process("mailPage.html", context);
        try {
            helper.setFrom(MAIL_FROM_ADDRESS, MailConstants.SENDER_NAME);
            helper.setTo(email);
            helper.setSubject(MailConstants.EMAIL_SUBJECT);
            helper.setText(template, true);
            mailSender.send(message);
        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new BusinessException(BusinessExceptionCode.ERROR_SENDING_MAIL);
        }
        return code.toString();
    }
}
