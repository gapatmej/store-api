package com.aperalta.store.web.rest.errors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<List<ApiError>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<ApiError> errors = new ArrayList<>();

        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            ApiError apiError = new ApiError(error.getDefaultMessage(), error.getField());
            errors.add(apiError);
        }

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}
