package com.louie.coding.dao;

import com.louie.coding.entity.Project;
import com.louie.coding.entity.ProjectFolder;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CanvasDao {
    Project getRecentProjectByUserId(Long userId);

    void addProjectFolder(ProjectFolder projectFolder);
}
