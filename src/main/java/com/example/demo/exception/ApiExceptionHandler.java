package com.example.demo.exception;

import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.webjars.NotFoundException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<?> apiExceptionHandler(BadRequestException e) {
        HttpStatus statusCode = HttpStatus.BAD_REQUEST;

        ApiException apiException = new ApiException(
                e.getMessage(),
                statusCode
        );
        return new ResponseEntity<>(apiException, statusCode);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> apiExceptionHandler(NotFoundException e) {
        HttpStatus statusCode = HttpStatus.NOT_FOUND;

        ApiException apiException = new ApiException(
                e.getMessage(),
                statusCode
        );
        return new ResponseEntity<>(apiException, statusCode);
    }


    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> apiExceptionHandler(ConstraintViolationException e) {
        HttpStatus statusCode = HttpStatus.UNPROCESSABLE_ENTITY;
        System.out.println();
        List<String> errorMessages = e.getConstraintViolations()
                .stream()
                .map(ConstraintViolation::getMessage).toList();
        ApiException apiException = new ApiException(
                errorMessages.toString(),
                statusCode
        );
        return new ResponseEntity<>(apiException, statusCode);
    }
}
