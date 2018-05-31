package com.demo.friendship.configuration;

import com.demo.friendship.controller.exception.RequestValidationException;
import com.demo.friendship.controller.message.ErrorResp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class ExceptionControllerAdvice {
    private static final Logger log = LoggerFactory.getLogger(ExceptionControllerAdvice.class);

    @Order(1)
    @ExceptionHandler(RequestValidationException.class)
    public ResponseEntity<ErrorResp> validationExceptionHandler(Exception e) {
        log.error("Validation Failure", e);
        return ResponseEntity.badRequest().body(new ErrorResp(e.getMessage()));
    }

    @Order(Ordered.LOWEST_PRECEDENCE)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResp> generalExceptionHandler(Exception e) {
        log.error("Unknown API Error", e);
        return ResponseEntity.badRequest().body(new ErrorResp("Error on API"));
    }

}
