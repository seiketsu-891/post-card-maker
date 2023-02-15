package com.louie.coding.dao;

import com.louie.coding.entity.RefreshTokenDetail;
import com.louie.coding.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao {
    User getUserByEmail(String email);

    void addUser(User user);

    Boolean getIsPremiumByUserId(Long id);

    void deleteRefreshToken(String token);

    void addRefreshToken(RefreshTokenDetail refreshTokenDetail);

    RefreshTokenDetail getRefreshTokenDetail(String token);
}
