package com.louie.coding.dao;

import com.louie.coding.entity.Project;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CanvasDao {
    Project getRecentProjectByUserId(Long userId);
}
