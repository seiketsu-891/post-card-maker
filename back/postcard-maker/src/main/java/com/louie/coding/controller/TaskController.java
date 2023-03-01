package com.louie.coding.controller;

import com.louie.coding.controller.support.UserSupport;
import com.louie.coding.entity.JsonResponse;
import com.louie.coding.service.TaskService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

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
    public JsonResponse<Map<String, Object>> getTasks() {
        Long userId = userSupport.getCurrentUserId();
        Map<String, Object> res = taskService.getTasks(userId);
        return JsonResponse.success(res);
    }

    /**
     * 添加任务完成情况
     */
    @PostMapping("/user-tasks")
    public JsonResponse<String> addUserTask(@RequestParam Long taskId) {
        Long userId = userSupport.getCurrentUserId();
        taskService.completeTask(userId, taskId);
        return JsonResponse.success();
    }
}
