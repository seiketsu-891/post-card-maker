package com.louie.coding.entity.entityReq;

import com.louie.coding.entity.annotation.ValidPassword;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

public class UserRestPassword {
    @Email(message = "邮箱非法")
    @NotNull(message = "邮箱不能为空")
    private String email;
    @NotNull(message = "验证码不能为空")
    private String code;
    @ValidPassword
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
