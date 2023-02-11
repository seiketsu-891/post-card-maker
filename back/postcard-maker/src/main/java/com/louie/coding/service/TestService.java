package com.louie.coding.service;

import com.louie.coding.dao.TestDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class TestService {
    @Resource
    private TestDao testDao;

    public String getNameById(Integer id) {
        return testDao.getNameById(id);
    }
}
