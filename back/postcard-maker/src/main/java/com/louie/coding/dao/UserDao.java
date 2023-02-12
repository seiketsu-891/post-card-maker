package com.louie.coding.dao;

import com.louie.coding.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao {
    User getUserByEmail(String email);

    void addUser(User user);
}
