package com.louie.coding.entityReq;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UserRegister {
    @Email(message = "邮箱非法")
    private String email;
    @Pattern(regexp = "/^[a-zA-z]\\w{3,10}$/", message = "用户名需要3-10位，字母开头，只含字母数字")
    private String username;
    private String code;
    @Size(min = 6, max = 16, message = "密码需要6-16位")
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
