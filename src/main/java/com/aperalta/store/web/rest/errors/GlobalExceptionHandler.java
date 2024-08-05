package com.aperalta.store.web.rest.errors;

import com.aperalta.store.web.rest.util.HeaderUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @ExceptionHandler(BadRequestAlertException.class)
    public ResponseEntity<Map<String, String>> handleBadRequestAlertException(BadRequestAlertException ex) {
        Map<String, String> body = new HashMap<>();
        body.put("error", ex.getMessage());
        body.put("entityName", ex.getEntityName());
        body.put("errorKey", ex.getErrorKey());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .headers(HeaderUtil.createFailureAlert(ex.getErrorKey(), ex.getMessage()))
                .body(body);
    }
}
