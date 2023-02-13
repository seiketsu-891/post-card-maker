package com.louie.coding.dao;

import com.louie.coding.entity.Album;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface AlbumDao {
    List<Album> getAlbumsWithPagination(Map<String, Object> params);
}
