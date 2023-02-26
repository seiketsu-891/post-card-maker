package com.louie.coding.interceptor;

import com.louie.coding.util.TokenUtil;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

@Component
public class TokenInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 放行options请求
        if (HttpMethod.OPTIONS.matches(request.getMethod())) {
            return true;
        }

        // 放行:注册/登录/找回密码/登出等
        List<String> allowUris = Arrays.asList(
                "/rsa-pks",
                "/users",
                "/verification-codes",
                "/double-tokens",
                "/passwords",
                "/refresh-tokens");
        String uri = request.getRequestURI();
        if (allowUris.contains(uri)) {
            return true;
        }

        String token = request.getHeader("token");
        // 刷新token的请求不用验证token，转而验证refreshToken
        if ("/tokens".equals(uri) && HttpMethod.POST.matches(request.getMethod())) {
            token = request.getHeader("refreshToken");
        }

        if (token != null) {
            Long userId;
            try {
                userId = TokenUtil.verifyToken(token);
            } catch (Exception e) {
                e.printStackTrace();
                response.setStatus(401);
                return false;
            }
            request.setAttribute("userId", userId);
            return true;
        }
        response.setStatus(401);
        return false;
    }
}
