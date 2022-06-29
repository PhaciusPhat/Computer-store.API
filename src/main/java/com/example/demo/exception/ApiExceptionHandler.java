package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.webjars.NotFoundException;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<?> apiExceptionHandler(BadRequestException e) {
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;

        ApiException apiException = new ApiException(
                e.getMessage(),
                badRequest
        );
        return new ResponseEntity<>(apiException, badRequest);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> apiExceptionHandler(NotFoundException e) {
        HttpStatus badRequest = HttpStatus.NOT_FOUND;

        ApiException apiException = new ApiException(
                e.getMessage(),
                badRequest
        );
        return new ResponseEntity<>(apiException, badRequest);
    }
}
