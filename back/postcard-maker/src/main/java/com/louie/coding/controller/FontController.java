package com.louie.coding.controller;

import com.louie.coding.entity.JsonResponse;
import com.louie.coding.entityResp.FontResp;
import com.louie.coding.service.FontService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class FontController {
    @Resource
    private FontService fontService;

    /**
     * 获取所有字体
     */
    @GetMapping("/fonts")
    public JsonResponse<List<FontResp>> getFonts() {
        List<FontResp> fontList = fontService.getFonts();
        return JsonResponse.success(fontList);
    }
}
