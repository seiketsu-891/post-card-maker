package com.louie.coding.controller;

import com.louie.coding.controller.support.UserSupport;
import com.louie.coding.entity.JsonResponse;
import com.louie.coding.entity.PageResult;
import com.louie.coding.entity.UserIllustration;
import com.louie.coding.entityResp.IllustrationResp;
import com.louie.coding.service.IllustrationService;
import com.louie.coding.util.Base64Util;
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

    @PostMapping("/test-snapshots")
    public JsonResponse<String> testUploadSnapshot() {
        String base64 = "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";
        MultipartFile multipartFile = null;
        try {
            multipartFile = Base64Util.base64PNGToMultipartFile(base64);
        } catch (Exception e) {
            e.printStackTrace();
        }
        illustrationService.uploadUserIllustration(multipartFile, 1L);
        return JsonResponse.success();
    }
}
