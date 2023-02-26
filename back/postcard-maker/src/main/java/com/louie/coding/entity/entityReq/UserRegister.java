package com.louie.coding.entity.entityReq;

import com.louie.coding.entity.annotation.ValidPassword;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class UserRegister {
    @Email(message = "邮箱非法")
    @NotNull(message = "邮箱不能为空")
    private String email;
    @NotNull(message = "用户名不能为空")
    @Pattern(regexp = "^[a-zA-Z][a-zA-Z0-9]{2,9}$", message = "用户名需要3-10位，字母开头，只含字母数字")
    private String username;
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
