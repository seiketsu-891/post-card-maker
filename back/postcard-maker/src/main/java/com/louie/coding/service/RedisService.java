package com.louie.coding.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Service
public class RedisService {
    private static final Logger LOGGER = LoggerFactory.getLogger(RedisService.class);
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private RedisTemplate<String, Boolean> booleanRedisTemplate;

    public void setStringValue(String key, String value, Long timeSeconds) {
        try {
            ValueOperations<String, String> vo = stringRedisTemplate.opsForValue();
            vo.set(key, value, timeSeconds, TimeUnit.SECONDS);
        } catch (Exception e) {
            LOGGER.info("redis存入失败, e={}", e.getMessage());
            e.printStackTrace();
        }
    }

    public void setBooleanValue(String key, Boolean value) {
        try {
            ValueOperations<String, Boolean> vo = booleanRedisTemplate.opsForValue();
            vo.set(key, value);
        } catch (Exception e) {
            LOGGER.info("redis存入失败, e={}", e.getMessage());
            e.printStackTrace();
        }
    }

    public Boolean getBooleanValue(String key) {
        ValueOperations<String, Boolean> vo = booleanRedisTemplate.opsForValue();
        return vo.get(key);
    }

    public String getStringValue(String key) {
        ValueOperations<String, String> vo = stringRedisTemplate.opsForValue();
        return vo.get(key);
    }

    public void deleteKey(String key) {
        try {
            stringRedisTemplate.delete(key);
        } catch (Exception e) {
            LOGGER.info("redis删除key失败, e={}", e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 获取key剩余有效期（秒数）
     */
    public Long getLeftValidTimeInSeconds(String key) {
        return stringRedisTemplate.getExpire(key);
    }
}
