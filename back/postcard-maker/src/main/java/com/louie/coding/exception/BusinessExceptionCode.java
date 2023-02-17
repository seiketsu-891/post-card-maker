package com.louie.coding.exception;

public enum BusinessExceptionCode {
    LOGIN_ERROR("邮箱密码不匹配"),
    USER_EMAIL_EXISTS("该电子邮箱已被占用"),
    ERROR_GET_RSA_KEY("获取RSA密钥失败"),
    ERROR_PASSWORD_DECRYPTION("密码解密失败"),
    ERROR_SENDING_MAIL("邮件发送失败"),
    MAIL_SEND_TOO_OFTEN("邮件发送过于频繁"),
    WRONG_VERIFICATION_CODE("验证码错误"),
    USER_EMAIL_NOT_EXISTS("该邮箱未注册"), ILLEGAL_ARGS("参数异常"), PROJECT_NOT_EXISTS("当前明信片不存在");

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
