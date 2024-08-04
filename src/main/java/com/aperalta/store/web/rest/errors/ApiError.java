package com.aperalta.store.web.rest.errors;

public class ApiError {

    private String message;
    private String field;

    public ApiError(String message, String field) {
        this.message = message;
        this.field = field;
    }

    // Getters and setters

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }
}
