package com.louie.coding.constants;

public interface MailConstants {
    String SENDER_NAME = "Louie";
    String EMAIL_SUBJECT = "postcard-maker：验证您的电子邮件";
    // 将邮件验证码存入redis时的key前缀
    String REDIS_KEY_MAIL_CODE_PREFIX = "REDIS_MAIL";
    // 验证码有效期
    Long MAIL_CODE_VALID_TIME = (long) (5 * 60 * 1000);
    // 验证码最少发送频率间隔
    Long MAIL_CODE_RESEND_MIN_INTERVAL = (long) (60 * 1000);
}
