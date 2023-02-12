package com.louie.coding.controller.support;

import com.louie.coding.util.TokenUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
public class UserSupport {
    // 这个方法的作用其实有两个，一个是实现token是否过期的验证，一个是获取一些业务逻辑需要的用户id
    public Long getCurrentUserId() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        String token = requestAttributes.getRequest().getHeader("token");
        Long userId = TokenUtil.verifyToken(token);
        // 这里我想过要不要验证这个id在数据库是否存在，但后来感觉没有必要
        // 因为用户用不存在的用户id理论上也无法获取任何数据
        return userId;
    }
}
