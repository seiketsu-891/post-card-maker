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
import java.util.List;

@Validated
@RestController
public class AlbumController {
    @Resource
    private AlbumService albumService;

    @GetMapping("/albums")
    public JsonResponse<List<Album>> getAlbums(@RequestParam @NotNull(message = "参数异常") Integer pageNum, @RequestParam @NotNull(message = "参数异常") Integer pageSize) {
        List<Album> albums = albumService.getAlbums(pageNum, pageSize);
        return JsonResponse.success(albums);
    }
}
