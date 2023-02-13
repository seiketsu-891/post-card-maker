package com.louie.coding.service;

import com.louie.coding.dao.IllustrationDao;
import com.louie.coding.entity.Illustration;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IllustrationService {
    @Resource
    private IllustrationDao illustrationDao;

    public List<Illustration> getIllustrations(Integer pageNum, Integer pageSize, Long albumId) {
        Integer start = (pageNum - 1) * pageSize;
        Map<String, Object> params = new HashMap<>();
        params.put("start", start);
        params.put("size", pageSize);
        params.put("albumId", albumId);

        List<Illustration> illustrations = illustrationDao.getIllusWithPagination(params);
        return illustrations;
    }
}
