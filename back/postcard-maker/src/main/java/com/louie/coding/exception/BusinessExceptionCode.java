package com.louie.coding.exception;

public enum BusinessExceptionCode {
    LOGIN_ERROR("邮箱密码不匹配"),
    USER_EMAIL_EXISTS("该电子邮箱已被占用"),
    ERROR_GET_RSA_KEY("获取RSA密钥失败"),
    TOKEN_EXPIRED("token已过期"),
    TOKEN_ILLEGAL("非法token"),
    ERROR_PASSWORD_DECRYPTION("密码解密失败"),
    ERROR_SENDING_MAIL("邮件发送失败"),
    MAIL_SEND_TOO_OFTEN("邮件发送过于频繁");

    private String desc;

    BusinessExceptionCode(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
