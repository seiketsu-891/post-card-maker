package com.louie.coding.controller;

import com.louie.coding.controller.support.UserSupport;
import com.louie.coding.entity.JsonResponse;
import com.louie.coding.entity.Project;
import com.louie.coding.service.CanvasService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class CanvasController {
    @Resource
    private CanvasService canvasService;
    @Resource
    private UserSupport userSupport;

    @GetMapping("/recent-projects")
    public JsonResponse<Project> getRecentProject() {
        Long userId = userSupport.getCurrentUserId();
        Project p = canvasService.getRecentProject(userId);
        // Project p = canvasService.getRecentProject(1L); 快速测试用
        return JsonResponse.success(p);
    }
}