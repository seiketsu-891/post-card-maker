package com.louie.coding.controller;

import com.louie.coding.controller.support.UserSupport;
import com.louie.coding.entity.JsonResponse;
import com.louie.coding.entityReq.UserLogin;
import com.louie.coding.entityReq.UserRegister;
import com.louie.coding.service.UserService;
import com.louie.coding.util.RSAUtil;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Map;

@RestController
@Validated
public class UserController {
    @Resource
    private UserService userService;
    @Resource
    private UserSupport userSupport;

    /**
     * 获取RSA公钥，主要是为了实现前端密码加密
     */
    @GetMapping("/rsa-pks")
    public JsonResponse<String> getRSAPublicKey() {
        String key = RSAUtil.getPublicKeyStr();
        return JsonResponse.success(key);
    }

    /**
     * 新增用户,新增后用户自动处于登录状态
     */
    @PostMapping("/users")
    public void addUser(@Valid @RequestBody UserRegister userRegister) {
        userService.addUser(userRegister);
    }

    /**
     * 发送邮箱验证码
     */
    @PostMapping("/verification-codes")
    public JsonResponse<String> sendVerificationCode(@RequestParam String email) {
        userService.sendVerificationCodeByMail(email);
        return JsonResponse.success();
    }

    /**
     * 用户登录
     *
     * @return token 已登录用户的信息从token中获取
     */
    @PostMapping("/double-tokens")
    public JsonResponse<Map<String, Object>> getToken(@RequestBody UserLogin user) {
        Map<String, Object> tokens = userService.login(user);
        return JsonResponse.success(tokens);
    }


    @GetMapping("/premiums")
    public JsonResponse<Boolean> checkIfPremium() {
        Long userId = userSupport.getCurrentUserId();
        Boolean isPremium = userService.checkIfPremium(userId);
        return JsonResponse.success(isPremium);
    }

    @GetMapping("/tokens")
    public JsonResponse<String> getNewToken(HttpServletRequest request) {
        String refreshToken = request.getHeader("refreshToken");
        String token = userService.refreshToken(refreshToken);
        return JsonResponse.success(token);
    }
}
