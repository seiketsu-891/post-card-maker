package com.louie.coding.controller;

import com.louie.coding.entity.JsonResponse;
import com.louie.coding.exception.BusinessException;
import com.louie.coding.exception.BusinessExceptionCode;
import com.louie.coding.service.TestService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class TestController {
    @Resource
    private TestService testService;

    @GetMapping("/test")
    public JsonResponse<String> getName(Integer id) {
        String name = testService.getNameById(id);
        return JsonResponse.success(name);
    }

    @GetMapping("/test-err")
    public JsonResponse<String> getNameError(Integer id) {
        String name = testService.getNameById(id);
        if (name.equals("Alex")) {
            throw new BusinessException(BusinessExceptionCode.LOGIN_ERROR);
        }
        return JsonResponse.success(name);
    }
}
