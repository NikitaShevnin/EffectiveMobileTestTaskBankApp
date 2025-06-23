package com.example.bankcards.exception;

/**
 * Простой DTO-объект для передачи информации об ошибке в ответах API.
 */

import java.time.LocalDateTime;
public class ApiError {
    private LocalDateTime timestamp;
    private String message;

    public ApiError(LocalDateTime timestamp, String message) {
        this.timestamp = timestamp;
        this.message = message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}