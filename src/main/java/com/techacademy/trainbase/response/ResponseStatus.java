package com.techacademy.trainbase.response;

public enum ResponseStatus {

    SUCCESS("SUCCESS", "Operation completed successfully"),
    CREATED("CREATED", "Resource created successfully"),
    UPDATED("UPDATED", "Resource updated successfully"),
    DELETED("DELETED", "Resource deleted successfully"),
    VALIDATION_ERROR("VALIDATION_ERROR", "Validation failed"),
    NOT_FOUND("NOT_FOUND", "Resource not found"),
    UNAUTHORIZED("UNAUTHORIZED", "Authentication required"),
    FORBIDDEN("FORBIDDEN", "Insufficient permissions"),
    CONFLICT("CONFLICT", "Resource conflict detected"),
    INTERNAL_ERROR("INTERNAL_ERROR", "Internal server error"),
    BAD_REQUEST("BAD_REQUEST", "Invalid request parameters"),
    USER_EXISTS("USER_EXISTS", "User already exists"),
    INVALID_CREDENTIALS("INVALID_CREDENTIALS", "Invalid credentials provided"),
    ACCOUNT_LOCKED("ACCOUNT_LOCKED", "Account is locked"),
    TOKEN_EXPIRED("TOKEN_EXPIRED", "Authentication token expired");

    private final String code;
    private final String message;

    ResponseStatus(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
