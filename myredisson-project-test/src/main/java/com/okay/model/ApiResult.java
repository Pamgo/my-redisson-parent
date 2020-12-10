package com.okay.model;

public class ApiResult {

    private Integer code;

    private String message;

    private Object data;

    public ApiResult(){}

    public ApiResult(Integer code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static ApiResult ok(Object data) {
        return new ApiResult(10200, "success", data);
    }

    public static ApiResult ok() {
        return new ApiResult(10200,"success",null);
    }

    public static ApiResult fail(String message) {
        return new ApiResult(10500, message, null);
    }

    public static ApiResult fail() {
        return new ApiResult(10500,"fail",null);
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message == null ? null : message.trim();
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ApiResult{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}