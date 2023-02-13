package com.louie.coding.controller;

import com.louie.coding.controller.support.UserSupport;
import com.louie.coding.entity.JsonResponse;
import com.louie.coding.entity.Project;
import com.louie.coding.service.CanvasService;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;

public class CanvasController {
    @Resource
    private CanvasService canvasService;
    @Resource
    private UserSupport userSupport;

    @GetMapping("/recent-projects")
    public JsonResponse<Project> getRecentProject() {
        Long userId = userSupport.getCurrentUserId();
        Project p = canvasService.getRecentProject(userId);
        return JsonResponse.success(p);
    }
}
