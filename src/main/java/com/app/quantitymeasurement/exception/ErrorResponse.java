package com.app.quantitymeasurement.exception;

import java.time.LocalDateTime;

public class ErrorResponse {
    public LocalDateTime timestamp;
    public int status;
    public String error;
    public String message;
    public String path;

    public ErrorResponse() {
    }

    public ErrorResponse(LocalDateTime timestamp, int status, String error, String message, String path) {
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
    }
}
