package com.louie.coding.service;

import com.louie.coding.dao.CanvasDao;
import com.louie.coding.entity.Project;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class CanvasService {
    @Resource
    private CanvasDao canvasDao;

    public Project getRecentProject(Long userId) {
        return canvasDao.getRecentProjectByUserId(userId);
    }
}
