package com.louie.coding.dao;

import com.louie.coding.entity.Font;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FontDao {
    List<Font> getFonts();
}
