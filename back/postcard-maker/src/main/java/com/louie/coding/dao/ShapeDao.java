package com.louie.coding.dao;

import com.louie.coding.entity.Shape;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ShapeDao {
    List<Shape> getShapes();
}
