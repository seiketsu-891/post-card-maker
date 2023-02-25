package com.louie.coding.controller;

import com.louie.coding.controller.support.UserSupport;
import com.louie.coding.entity.JsonResponse;
import com.louie.coding.entity.Project;
import com.louie.coding.entity.ProjectFolder;
import com.louie.coding.service.CanvasService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class CanvasController {
    @Resource
    private CanvasService canvasService;
    @Resource
    private UserSupport userSupport;

    /**
     * 获取最近一次编辑过的画布
     */
    @GetMapping("/recent-projects")
    public JsonResponse<Project> getRecentProject() {
        Long userId = userSupport.getCurrentUserId();
        Project p = canvasService.getRecentProject(userId);
        // Project p = canvasService.getRecentProject(1L); 快速测试用
        return JsonResponse.success(p);
    }

    //
    @PostMapping("/project-folders")
    public JsonResponse<String> addProjectFolder(@RequestBody ProjectFolder projectFolder) {
        Long userId = userSupport.getCurrentUserId();
        canvasService.addProjectFolder(projectFolder, userId);
        return JsonResponse.success();
    }

    /**
     * 新建一个明信片项目
     */
    @PostMapping("/projects")
    public JsonResponse<Project> addProject(@RequestBody Project project) {
        Long userId = userSupport.getCurrentUserId();
        Project projectWithCanvasAndElements = canvasService.addProject(project, userId);
        return JsonResponse.success(projectWithCanvasAndElements);
    }
//
//    /**
//     * 项目已新建好的情况下，更新或新增明信片项目元素
//     */
//    public JsonResponse<Long> addOrUpdateElements(Element element) {
//        Long userId = userSupport.getCurrentUserId();
//        Long elementId =  canvasService.addOrUpdateElements(element, userId);
//        return JsonResponse.success(elementId);
//    }
//    public JsonResponse<String> updateCanvas(Canvas canvas) {
//
//    }
//    public JsonResponse<String> updateProjectInfo(Project project){
//
//    }

}
