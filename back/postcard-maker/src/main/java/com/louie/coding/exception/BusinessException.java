package com.louie.coding.exception;

/**
 * 业务上的异常，例如账号密码不一致等
 */
public class BusinessException extends RuntimeException {
    private BusinessExceptionCode code;

    public BusinessException(BusinessExceptionCode code) {
        this.code = code;
    }

    public BusinessExceptionCode getCode() {
        return code;
    }

    public void setCode(BusinessExceptionCode code) {
        this.code = code;
    }
}
