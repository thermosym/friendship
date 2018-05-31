package com.demo.friendship.configuration;

import com.demo.friendship.controller.exception.RequestValidationException;
import com.demo.friendship.controller.message.ErrorResp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;


@ControllerAdvice
public class ExceptionControllerAdvice {
    private static final Logger log = LoggerFactory.getLogger(ExceptionControllerAdvice.class);

    @Order(1)
    @ExceptionHandler(RequestValidationException.class)
    public ResponseEntity<ErrorResp> validationExceptionHandler(Exception e) {
        log.error("Validation Failure", e);
        return ResponseEntity.ok(new ErrorResp(e.getMessage()));
    }

    @Order(2)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResp> validationExceptionHandler(MethodArgumentNotValidException e) {
        log.error("Validation failed on HTTP", e);
        List<ObjectError> allErrors = e.getBindingResult().getAllErrors();
        String errorMsg = allErrors.stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining(", "));
        return ResponseEntity.ok(new ErrorResp(errorMsg));
    }

    @Order(Ordered.LOWEST_PRECEDENCE)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResp> generalExceptionHandler(Exception e) {
        log.error("Unknown API Error", e);
        return ResponseEntity.ok(new ErrorResp("Unknown Error"));
    }

}
