package com.louie.coding.service;

import com.louie.coding.dao.TaskDao;
import com.louie.coding.entity.entityResp.TaskResp;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TaskService {
    @Resource
    private TaskDao taskDao;

    public List<TaskResp> getTasks(Long userId) {
        return taskDao.getTasks(userId);
    }
}
