package com.louie.coding.entityReq;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

public class UserLogin {
    @Email(message = "邮箱非法")
    private String email;
    @Size(min = 6, max = 16, message = "密码需要6-16位")
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
