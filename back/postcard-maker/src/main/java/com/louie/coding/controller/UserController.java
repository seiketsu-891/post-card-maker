package com.louie.coding.controller;

import com.louie.coding.entity.JsonResponse;
import com.louie.coding.entity.User;
import com.louie.coding.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class UserController {
    @Resource
    private UserService userService;

    /**
     * 新增用户,新增后用户自动处于登录状态
     */
    @PostMapping("/users")
    public JsonResponse<String> addUser(@RequestBody User user) {
        String token = userService.addUser(user);
        return JsonResponse.success(token);
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
}
