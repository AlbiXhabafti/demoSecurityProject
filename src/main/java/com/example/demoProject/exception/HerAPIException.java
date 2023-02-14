package com.example.demoProject.exception;

import org.springframework.http.HttpStatus;

public class HerAPIException extends RuntimeException {

    private final HttpStatus status;
    private final String message;

    public HerAPIException(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public HerAPIException(String message, HttpStatus status, String message1) {
        super(message);
        this.status = status;
        this.message = message1;
    }

    public HttpStatus getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return message;
    }
}