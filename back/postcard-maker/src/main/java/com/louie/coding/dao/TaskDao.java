package com.louie.coding.dao;

import com.louie.coding.entity.entityResp.TaskResp;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TaskDao {
    List<TaskResp> getTasks(Long userId);
}
