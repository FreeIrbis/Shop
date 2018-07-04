package com.shop.controller.exception;

import java.util.Date;

public class ExceptionResponse {

    private Date timestamp;
    private int status;
    private String error;
    private String message;
    private String path;

    public ExceptionResponse() {
        this.timestamp = new Date();
    }

    public ExceptionResponse(String message, String path) {
        this.timestamp = new Date();
        this.message = message;
        this.path = path;
        this.status = 400;
        this.error = "Bad Request";
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }

    public String getPath() {
        return path;
    }

    public int getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    @Override
    public String toString() {
        return "ExceptionResponse{" +
                "timestamp=" + timestamp +
                ", status=" + status +
                ", error='" + error + '\'' +
                ", message='" + message + '\'' +
                ", path='" + path + '\'' +
                '}';
    }
}
