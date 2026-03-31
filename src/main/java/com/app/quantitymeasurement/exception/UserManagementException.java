package com.app.quantitymeasurement.exception;

import org.springframework.http.HttpStatus;

public class UserManagementException extends RuntimeException {

    private final HttpStatus status;

    public UserManagementException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
