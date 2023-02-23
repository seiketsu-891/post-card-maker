package com.louie.coding.controller;

import com.louie.coding.controller.support.UserSupport;
import com.louie.coding.entity.JsonResponse;
import com.louie.coding.service.FileService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@RestController
public class FileController {
    @Resource
    private FileService fileService;
    @Resource
    private UserSupport userSupport;

    /**
     * 上传文件
     */
    @PostMapping("/files")
    public JsonResponse<String> uploadFile(MultipartFile file) {
        Long userId = userSupport.getCurrentUserId();
        fileService.uploadFile(file, userId);
        //        fileService.uploadFile(file, 1L);
        return JsonResponse.success();
    }
}
