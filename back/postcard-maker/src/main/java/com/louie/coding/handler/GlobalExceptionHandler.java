package com.louie.coding.handler;

import com.louie.coding.entity.JsonResponse;
import com.louie.coding.exception.BusinessException;
import com.louie.coding.exception.BusinessExceptionCode;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public JsonResponse<String> globalExceptionHandler(Exception e) {
        String message = e.getMessage();
        if (e instanceof BusinessException) {
            BusinessExceptionCode code = ((BusinessException) e).getCode();
            message = code.getDesc();
            return JsonResponse.error(0, message);
        }
        return JsonResponse.error(message);
    }
}
