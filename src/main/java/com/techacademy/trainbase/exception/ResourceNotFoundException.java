package com.techacademy.trainbase.exception;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String resource, String field, Object value) {
        super(String.format("%s with %s %s not found", resource, field, value));
    }
}
