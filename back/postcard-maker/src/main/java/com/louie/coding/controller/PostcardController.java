package com.louie.coding.controller;

import com.louie.coding.controller.support.UserSupport;
import com.louie.coding.entity.JsonResponse;
import com.louie.coding.entity.Postcard;
import com.louie.coding.entity.PostcardContent;
import com.louie.coding.service.PostcardService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
public class PostcardController {
    @Resource
    private PostcardService postcardService;
    @Resource
    private UserSupport userSupport;

    /**
     * 更新或新建明信片（用户进行一个操作时触发）
     */
    @PostMapping("/postcards")
    public JsonResponse<String> addOrUpdatePostcard(@RequestBody PostcardContent postcardContent) {
        Long userId = userSupport.getCurrentUserId();
        postcardService.addOrUpdatePostcard(userId, postcardContent);
        return JsonResponse.success();
    }

    /**
     * 获取明信片（读取最新编辑的明信片或者根据id读取某一特定明信片）
     */
    @GetMapping(value = {"/postcards/{id}", "/postcards"})
    // path-variable可选时，上面的路径需要写两个，不然在没有传参数时会报cors error
    public JsonResponse<Postcard> getPostcard(@PathVariable(required = false) Long id) {
        Long userId = userSupport.getCurrentUserId();
        Postcard postcard = postcardService.getPostcard(userId, id);
        return JsonResponse.success(postcard);
    }
//    /**
//     * 更新画布
//     */
//    @PostMapping("/canvases")t
//    public JsonResponse<String> updateCanvas(@RequestBody Canvas canvas) {
//        Long userId = userSupport.getCurrentUserId();
//        postcardService.updateCanvas(canvas, userId);
//        return JsonResponse.success();
//    }

//    /**
//     * 更新明信片信息（目前就是明信片名字）
//     */
//    @PostMapping("/project-info")
//    public JsonResponse<String> updateProjectInfo(@RequestBody Postcard postcard) {
//        Long userId = userSupport.getCurrentUserId();
//        postcardService.updateProjectInfo(postcard, userId);
//        return JsonResponse.success();
//    }

//    /**
//     * 获取当前用户的所有明信片信息（分页获取）
//     */
//    @GetMapping("/postcards")
//    public JsonResponse<PageResult<Postcard>> projectList(@RequestParam Integer pageNum, @RequestParam Integer pageSize) {
//        Long userId = userSupport.getCurrentUserId();
//        PageResult<Postcard> res = postcardService.getPostcardList(pageNum, pageSize, userId);
//        return JsonResponse.success(res);
//    }
}
