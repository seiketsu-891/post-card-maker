package com.louie.coding.entity;

public class JsonResponse<T> {
    private T data;
    private String message;
    private int code;

    public JsonResponse(String message, int code, T data) {
        this.data = data;
        this.message = message;
        this.code = code;
    }

    public static JsonResponse<String> success() {
        return new JsonResponse<>("success", 200, null);
    }

    public static <T> JsonResponse<T> success(T data) {
        return new JsonResponse<>("success", 200, data);
    }

    public static JsonResponse<String> error(String message) {
        return new JsonResponse<>(message, 500, null);
    }

    public static JsonResponse<String> error(int code, String message) {
        return new JsonResponse<>(message, code, null);
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
