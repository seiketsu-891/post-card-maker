package com.louie.coding.controller;

import com.louie.coding.controller.support.UserSupport;
import com.louie.coding.entity.JsonResponse;
import com.louie.coding.entity.entityResp.TaskResp;
import com.louie.coding.service.TaskService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class TaskController {
    @Resource
    private UserSupport userSupport;
    @Resource
    private TaskService taskService;

    /**
     * 获取成为会员的任务及用户完成情况
     */
    @GetMapping("/tasks")
    public JsonResponse<List<TaskResp>> getTasks() {
        Long userId = userSupport.getCurrentUserId();
        List<TaskResp> res = taskService.getTasks(userId);
        return JsonResponse.success(res);
    }
}
