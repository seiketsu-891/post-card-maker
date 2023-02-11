package com.louie.coding.exception;

public enum BusinessExceptionCode {
    LOGIN_ERROR("用户名密码不匹配");

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
