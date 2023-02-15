package com.louie.coding.exception;

public enum AuthExceptionCode {
    TOKEN_ILLEGAL("token不合法"),
    TOKEN_EXPIRED("token过期");
    private String desc;

    AuthExceptionCode(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
