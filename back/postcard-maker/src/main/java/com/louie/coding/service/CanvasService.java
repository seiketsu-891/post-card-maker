package com.louie.coding.service;

import com.louie.coding.dao.CanvasDao;
import com.louie.coding.entity.Project;
import com.louie.coding.entity.ProjectFolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class CanvasService {
    @Resource
    private CanvasDao canvasDao;

    public Project getRecentProject(Long userId) {
        return canvasDao.getRecentProjectByUserId(userId);
    }

    public void addProject(Project project) {

    }

    public void addProjectFolder(ProjectFolder projectFolder, Long userId) {
        projectFolder.setUserId(userId);
        projectFolder.setCreateTime(new Date());
        canvasDao.addProjectFolder(projectFolder);
    }
}
