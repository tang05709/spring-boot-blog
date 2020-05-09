package com.don.donaldblog.utils;

public class JsonUtils<T> {
    private Integer code;
    private String message;
    private T data;

    protected JsonUtils(){}

    protected JsonUtils(Integer code, String message, T data)
    {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> JsonUtils<T> success(T data)
    {
        return new JsonUtils<T>(0, "success", data);
    }

    public static <T> JsonUtils<T> fail(T data)
    {
        return new JsonUtils<T>(400, "fail", data);
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer  code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }


}
