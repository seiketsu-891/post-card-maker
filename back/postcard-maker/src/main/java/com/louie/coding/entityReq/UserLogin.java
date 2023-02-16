package com.louie.coding.entityReq;

import com.louie.coding.entity.annotation.ValidPassword;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

public class UserLogin {
    @Email(message = "邮箱非法")
    @NotNull(message = "邮箱不能为空")
    private String email;

    @ValidPassword
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
