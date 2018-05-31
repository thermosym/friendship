package com.demo.friendship.controller.exception;

public class RequestValidationException extends IllegalArgumentException {

    public RequestValidationException() {
    }

    public RequestValidationException(String msg) {
        super(msg);
    }
}
