package com.louie.coding.dao;

import com.louie.coding.entity.Illustration;
import com.louie.coding.entity.UserIllustration;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface IllustrationDao {
    List<Illustration> getIllusWithPagination(Map<String, Object> params);

    Integer getCount(Map<String, Object> params);

    void addUserIllustration(UserIllustration userIllustration);
}
