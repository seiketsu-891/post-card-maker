package com.louie.coding.dao;

import com.louie.coding.entity.Task;
import com.louie.coding.entity.UserTask;
import com.louie.coding.entity.entityResp.TaskResp;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TaskDao {
    List<TaskResp> getTasks(Long userId);

    Task getTaskById(Long id);

    void addUserTask(UserTask userTask);

    UserTask getUserTaskByTaskIdAndUserId(@Param("taskId") Long taskId, @Param("userId") Long userId);
}
