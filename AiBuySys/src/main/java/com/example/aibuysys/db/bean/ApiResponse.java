package com.example.aibuysys.db.bean;

import org.springframework.stereotype.Component;


public class ApiResponse {
    private boolean success;
    private String message;

    // 构造方法、getter 和 setter 方法

    public ApiResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}
