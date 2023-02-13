package com.louie.coding.service;

import com.louie.coding.dao.AlbumDao;
import com.louie.coding.entity.Album;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AlbumService {
    @Resource
    private AlbumDao albumDao;

    public List<Album> getAlbums(Integer pageNum, Integer pageSize) {
        Integer start = (pageNum - 1) * pageSize;
        Map<String, Object> params = new HashMap<>();
        params.put("start", start);
        params.put("size", pageSize);

        List<Album> albums = albumDao.getAlbumsWithPagination(params);
        // todo set count
        return albums;
    }
}
