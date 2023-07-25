package com.louie.coding.controller.support;

import com.louie.coding.constants.RedisConstants;
import com.louie.coding.service.RedisService;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;

@Component
public class UserSupport {
    @Resource
    RedisService redisService;

    public Long getCurrentUserId() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        Long userId = (Long) requestAttributes.getRequest().getAttribute("userId");
        return userId;
    }

    public Boolean getUserVipInfo() {
        Long userId = this.getCurrentUserId();
        return redisService.getBooleanValue(userId + RedisConstants.KEY_USER_IFVIP_SUFFIX);
    }
}
