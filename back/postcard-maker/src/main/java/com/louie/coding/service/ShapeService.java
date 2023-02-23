package com.louie.coding.service;

import com.louie.coding.dao.ShapeDao;
import com.louie.coding.entity.Shape;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ShapeService {
    @Resource
    private ShapeDao shapeDao;

    public List<Shape> getShapes() {
        return shapeDao.getShapes();
    }
}
