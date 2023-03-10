package com.louie.coding.dao;

import com.louie.coding.entity.Canvas;
import com.louie.coding.entity.Element;
import com.louie.coding.entity.Project;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Mapper
public interface CanvasDao {
    Project getRecentProjectByUserId(Long userId);

    void addProject(Project project);

    void addCanvas(Canvas canvas);

    void addElements(@Param("elements") List<Element> elements);

    void addElement(Element element);

    void updateElement(Element element);

    void updateProjectTimeByProjectIdAndUserId(@Param("updateTime") Date now, @Param("projectId") Long projectId, @Param("userId") Long userId);

    void updateCanvas(Canvas canvas);

    void updateProjectInfoByProjectIdAndUserId(Project project);

    Integer getProjectCount(Long userId);

    List<Project> getProjectsWithPagination(Map<String, Object> params);

}
