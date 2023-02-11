package com.louie.coding.dao;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TestDao {
    String getNameById(Integer id);
}
