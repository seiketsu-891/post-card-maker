package com.louie.coding.controller;

import com.louie.coding.entity.Illustration;
import com.louie.coding.entity.JsonResponse;
import com.louie.coding.service.IllustrationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class IllustrationController {
    @Resource
    private IllustrationService illustrationService;

    @GetMapping("/illustrations")
    public JsonResponse<List<Illustration>> getAlbums(@RequestParam Integer pageNum, @RequestParam Integer pageSize, @RequestParam Long albumId) {
        List<Illustration> illustrations = illustrationService.getIllustrations(pageNum, pageSize, albumId);
        return JsonResponse.success(illustrations);
    }

}
