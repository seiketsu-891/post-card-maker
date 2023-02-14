package com.louie.coding.controller;

import com.louie.coding.controller.support.UserSupport;
import com.louie.coding.entity.JsonResponse;
import com.louie.coding.entity.User;
import com.louie.coding.service.UserService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

@RestController
@Validated
public class UserController {
    @Resource
    private UserService userService;
    @Resource
    private UserSupport userSupport;

    /**
     * 新增用户,新增后用户自动处于登录状态
     */
    @PostMapping("/users")
    public JsonResponse<String> addUser(@Valid @RequestBody User user) {
        String token = userService.addUser(user);
        return JsonResponse.success(token);
    }

    /**
     * 发送邮箱验证码
     *
     * @param email
     * @return
     */
    @PostMapping("/verification-codes")
    public JsonResponse<String> sendVerificationCode(@RequestParam String email) {
        userService.sendVerificationCodeByMail(email);
        return JsonResponse.success();
    }

    /**
     * 用户登录
     *
     * @param user
     * @return token 已登录用户的信息从token中获取
     */
    @PostMapping("/sessions")
    public JsonResponse<String> getToken(@RequestBody User user) {
        String token = userService.login(user);
        return JsonResponse.success(token);
    }


    @GetMapping("/premiums")
    public JsonResponse<Boolean> checkIfPremium() {
        Long userId = userSupport.getCurrentUserId();
        Boolean isPremium = userService.checkIfPremium(userId);
        return JsonResponse.success(isPremium);
    }
}
