package com.louie.coding.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class RedisConfig {
    @Bean
    public RedisTemplate<String, Boolean> getBooleanRedisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Boolean> booleanRedisTemplate = new RedisTemplate<>();
        booleanRedisTemplate.setConnectionFactory(factory);
        return booleanRedisTemplate;
    }

}
