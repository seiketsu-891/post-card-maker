package com.louie.coding.dao;

import com.louie.coding.entity.RefreshTokenDetail;
import com.louie.coding.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserDao {
    User getUserByEmail(String email);

    void addUser(User user);

    Boolean getIsPremiumByUserId(Long id);

    void addRefreshToken(RefreshTokenDetail refreshTokenDetail);

    RefreshTokenDetail getRefreshTokenDetail(String token);

    void updatePassword(User user);

    void deleteRefreshTokenByUserIdAndToken(@Param("userId") Long userId, @Param("token") String refreshToken);
}
