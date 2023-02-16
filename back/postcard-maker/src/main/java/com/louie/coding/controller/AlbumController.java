package com.louie.coding.controller;

import com.louie.coding.entity.Album;
import com.louie.coding.entity.JsonResponse;
import com.louie.coding.service.AlbumService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;

@Validated
@RestController
public class AlbumController {
    @Resource
    private AlbumService albumService;

    @GetMapping("/albums")
    // 当controller上用@Valid的时候， @Positive 不起作用
    public JsonResponse<List<Album>> getAlbums(@RequestParam @Positive(message = "页码参数异常") @NotNull Integer pageNum, @RequestParam @Positive(message = "页码参数异常") @NotNull Integer pageSize) {
        List<Album> albums = albumService.getAlbums(pageNum, pageSize);
        return JsonResponse.success(albums);
    }
}
