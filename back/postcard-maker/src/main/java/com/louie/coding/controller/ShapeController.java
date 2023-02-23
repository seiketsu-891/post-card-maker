package com.louie.coding.controller;

import com.louie.coding.entity.JsonResponse;
import com.louie.coding.entity.Shape;
import com.louie.coding.service.ShapeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class ShapeController {
    @Resource
    private ShapeService shapeService;

    @GetMapping("/shapes")
    public JsonResponse<List<Shape>> getShapes() {
        List<Shape> shapes = shapeService.getShapes();
        return JsonResponse.success(shapes);
    }
}
