package com.demo.friendship.controller.exception;

public class ConnectionRejectException extends Exception {
    public ConnectionRejectException() {
    }

    public ConnectionRejectException(String message) {
        super(message);
    }

    public ConnectionRejectException(String message, Throwable cause) {
        super(message, cause);
    }
}
