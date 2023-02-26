package com.louie.coding.controller;

import com.louie.coding.controller.support.UserSupport;
import com.louie.coding.entity.*;
import com.louie.coding.service.CanvasService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

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

    /**
     * 项目已新建好的情况下，更新明信片项目元素
     */
    @PostMapping("/elements")
    public JsonResponse<String> addOrUpdateElements(@RequestBody Element element) {
        Long userId = userSupport.getCurrentUserId();
        canvasService.addOrUpdateElements(element, userId);
        return JsonResponse.success();
    }

    /**
     * 更新画布
     */
    @PostMapping("/canvases")
    // todo 传递全部还是部分属性到时候看前端测试决定
    public JsonResponse<String> updateCanvas(@RequestBody Canvas canvas) {
        Long userId = userSupport.getCurrentUserId();
        canvasService.updateCanvas(canvas, userId);
        return JsonResponse.success();
    }

    /**
     * 更新明信片信息（目前就是明信片名字）
     */
    @PostMapping("/project-info")
    public JsonResponse<String> updateProjectInfo(@RequestBody Project project) {
        Long userId = userSupport.getCurrentUserId();
        canvasService.updateProjectInfo(project, userId);
        return JsonResponse.success();
    }

    /**
     * 获取当前用户的所有明信片项目信息（分页获取）
     */
    @GetMapping("/projects")
    public JsonResponse<PageResult<Project>> projectList(@RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        Long userId = userSupport.getCurrentUserId();
        PageResult<Project> res = canvasService.getProjectList(pageNum, pageSize, userId);
        return JsonResponse.success(res);
    }

    /**
     * 获取当前用户的所有明信片项目文件夹
     */
    @GetMapping("/project-folders")
    public JsonResponse<List<ProjectFolder>> projectFolderList() {
        Long userId = userSupport.getCurrentUserId();
        List<ProjectFolder> res = canvasService.getProjectFolderList(userId);
        return JsonResponse.success(res);
    }
}
