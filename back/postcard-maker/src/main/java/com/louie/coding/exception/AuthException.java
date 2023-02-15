package com.louie.coding.exception;

public class AuthException extends RuntimeException {
    private AuthExceptionCode code;

    public AuthException(AuthExceptionCode code) {
        this.code = code;
    }

    public AuthExceptionCode getCode() {
        return code;
    }

    public void setCode(AuthExceptionCode code) {
        this.code = code;
    }
}
