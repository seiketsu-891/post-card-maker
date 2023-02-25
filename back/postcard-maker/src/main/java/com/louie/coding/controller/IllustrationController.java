package com.louie.coding.controller;

import com.louie.coding.controller.support.UserSupport;
import com.louie.coding.entity.JsonResponse;
import com.louie.coding.entity.PageResult;
import com.louie.coding.entity.UserIllustration;
import com.louie.coding.entityResp.IllustrationResp;
import com.louie.coding.service.IllustrationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@RestController
public class IllustrationController {
    @Resource
    private IllustrationService illustrationService;
    @Resource
    private UserSupport userSupport;

    @GetMapping("/illustrations")
    public JsonResponse<PageResult<IllustrationResp>> getAlbums(@RequestParam Integer pageNum, @RequestParam Integer pageSize, @RequestParam(required = false) Long albumId, @RequestParam(required = false) String keyword) {
        PageResult<IllustrationResp> res = illustrationService.getIllustrations(pageNum, pageSize, albumId, keyword);
        return JsonResponse.success(res);
    }

    @GetMapping("/user-pics")
    public JsonResponse<PageResult<UserIllustration>> getUserPics(@RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        Long currentUserId = userSupport.getCurrentUserId();
        PageResult<UserIllustration> res = illustrationService.getUserPics(pageNum, pageSize, currentUserId);
        return JsonResponse.success(res);
    }

    @PostMapping("/user-pics")
    public JsonResponse<String> uploadFile(MultipartFile file) {
        Long userId = userSupport.getCurrentUserId();
        illustrationService.uploadUserIllustration(file, userId);
        return JsonResponse.success();
    }
}
