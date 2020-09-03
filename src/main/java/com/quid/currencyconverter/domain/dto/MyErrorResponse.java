package com.quid.currencyconverter.domain.dto;

public class MyErrorResponse {
    public final String path;
    public final Integer status;
    public final String error;
    public final String message;

    public MyErrorResponse(String path, Integer status, String error, String message) {
        this.path = path;
        this.status = status;
        this.error = error;
        this.message = message;
    }

    @Override
    public String toString() {
        return "MyErrorResponse{" +
                "path='" + path + '\'' +
                ", status=" + status +
                ", error='" + error + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
