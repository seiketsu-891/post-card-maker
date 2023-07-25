package com.louie.coding.service;

import com.louie.coding.dao.TaskDao;
import com.louie.coding.entity.Task;
import com.louie.coding.entity.UserTask;
import com.louie.coding.entity.entityResp.TaskResp;
import com.louie.coding.exception.BusinessException;
import com.louie.coding.exception.BusinessExceptionCode;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TaskService {
    @Resource
    private TaskDao taskDao;

    public Map<String, Object> getTasks(Long userId) {
        List<TaskResp> tasks = taskDao.getTasks(userId);
        boolean allCompleted = true;
        for (TaskResp task : tasks) {
            if (!task.isCompleted()) {
                allCompleted = false;
                break;
            }
        }
        Map<String, Object> res = new HashMap<>();
        res.put("list", tasks);
        res.put("allCompleted", allCompleted);
        return res;
    }

    public void completeTask(Long userId, Long taskId) {
        UserTask userTask = this.getUserTaskByTaskIdAndUserId(taskId, userId);
        // 如果任务已完成过，则不做处理;
        if (userTask != null) {
            return;
        }
        // 查询任务是否有效
        Task task = taskDao.getTaskById(taskId);
        if (task == null || !task.getValid()) {
            throw new BusinessException(BusinessExceptionCode.ILLEGAL_ARGS);
        }

        userTask = new UserTask();
        userTask.setTaskId(taskId);
        userTask.setUserId(userId);
        userTask.setCompleteTime(new Date());
        taskDao.addUserTask(userTask);
    }

    public UserTask getUserTaskByTaskIdAndUserId(Long taskId, Long userId) {
        return taskDao.getUserTaskByTaskIdAndUserId(taskId, userId);
    }
}
