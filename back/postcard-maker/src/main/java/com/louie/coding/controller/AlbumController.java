package com.louie.coding.controller;

import com.louie.coding.entity.Album;
import com.louie.coding.entity.JsonResponse;
import com.louie.coding.service.AlbumService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class AlbumController {
    @Resource
    private AlbumService albumService;

    @GetMapping("/albums")
    public JsonResponse<List<Album>> getAlbums(@RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        List<Album> albums = albumService.getAlbums(pageNum, pageSize);
        return JsonResponse.success(albums);
    }
}
