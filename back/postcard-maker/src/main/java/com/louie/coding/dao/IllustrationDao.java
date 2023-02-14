package com.louie.coding.dao;

import com.louie.coding.entity.Illustration;

import java.util.List;
import java.util.Map;

public interface IllustrationDao {
    List<Illustration> getIllusWithPagination(Map<String, Object> params);

    Integer getCount(Map<String, Object> params);
}
