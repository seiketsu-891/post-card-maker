package com.louie.coding.controller;

import com.louie.coding.entity.Illustration;
import com.louie.coding.entity.JsonResponse;
import com.louie.coding.entity.PageResult;
import com.louie.coding.service.IllustrationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class IllustrationController {
    @Resource
    private IllustrationService illustrationService;

    @GetMapping("/illustrations")
    public JsonResponse<PageResult<Illustration>> getAlbums(@RequestParam Integer pageNum, @RequestParam Integer pageSize, @RequestParam(required = false) Long albumId, @RequestParam(required = false) String keyword) {
        PageResult<Illustration> res = illustrationService.getIllustrations(pageNum, pageSize, albumId, keyword);
        return JsonResponse.success(res);
    }

}
