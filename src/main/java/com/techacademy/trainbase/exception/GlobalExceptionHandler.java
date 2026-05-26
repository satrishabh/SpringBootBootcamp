package com.techacademy.trainbase.exception;

import com.techacademy.trainbase.response.ApiResponse;
import com.techacademy.trainbase.response.ResponseStatus;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleResourceNotFoundException(
            ResourceNotFoundException ex, HttpServletRequest request) {

        ApiResponse<Object> response = ApiResponse.builder()
            .success(false)
            .message(ex.getMessage())
            .timestamp(LocalDateTime.now())
            .statusCode(HttpStatus.NOT_FOUND.value())
            .status(HttpStatus.NOT_FOUND.getReasonPhrase())
            .errorCode(ResponseStatus.NOT_FOUND.getCode())
            .path(request.getRequestURI())
            .apiVersion("1.0")
            .build();

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleValidationExceptions(
            MethodArgumentNotValidException ex, HttpServletRequest request) {

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        ApiResponse<Map<String, String>> response = ApiResponse.<Map<String, String>>builder()
            .success(false)
            .message(ResponseStatus.VALIDATION_ERROR.getMessage())
            .data(errors)
            .timestamp(LocalDateTime.now())
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .status(HttpStatus.BAD_REQUEST.getReasonPhrase())
            .errorCode(ResponseStatus.VALIDATION_ERROR.getCode())
            .path(request.getRequestURI())
            .apiVersion("1.0")
            .validationErrors(errors)
            .build();

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleConstraintViolationException(
            ConstraintViolationException ex, HttpServletRequest request) {

        Map<String, String> errors = new HashMap<>();
        ex.getConstraintViolations().forEach(violation -> {
            String fieldName = violation.getPropertyPath().toString();
            String errorMessage = violation.getMessage();
            errors.put(fieldName, errorMessage);
        });

        ApiResponse<Map<String, String>> response = ApiResponse.<Map<String, String>>builder()
            .success(false)
            .message(ResponseStatus.VALIDATION_ERROR.getMessage())
            .data(errors)
            .timestamp(LocalDateTime.now())
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .status(HttpStatus.BAD_REQUEST.getReasonPhrase())
            .errorCode(ResponseStatus.VALIDATION_ERROR.getCode())
            .path(request.getRequestURI())
            .apiVersion("1.0")
            .validationErrors(errors)
            .build();

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiResponse<Object>> handleBusinessException(
            BusinessException ex, HttpServletRequest request) {

        HttpStatus httpStatus = determineHttpStatus(ex.getResponseStatus());
        ApiResponse<Object> response = ApiResponse.builder()
            .success(false)
            .message(ex.getMessage())
            .timestamp(LocalDateTime.now())
            .statusCode(httpStatus.value())
            .status(httpStatus.getReasonPhrase())
            .errorCode(ex.getErrorCode())
            .path(request.getRequestURI())
            .apiVersion("1.0")
            .build();

        return new ResponseEntity<>(response, httpStatus);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<Object>> handleIllegalArgumentException(
            IllegalArgumentException ex, HttpServletRequest request) {

        ApiResponse<Object> response = ApiResponse.builder()
            .success(false)
            .message(ex.getMessage())
            .timestamp(LocalDateTime.now())
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .status(HttpStatus.BAD_REQUEST.getReasonPhrase())
            .errorCode(ResponseStatus.BAD_REQUEST.getCode())
            .path(request.getRequestURI())
            .apiVersion("1.0")
            .build();

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleGlobalException(
            Exception ex, HttpServletRequest request) {

        ApiResponse<Object> response = ApiResponse.builder()
            .success(false)
            .message(ResponseStatus.INTERNAL_ERROR.getMessage())
            .timestamp(LocalDateTime.now())
            .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
            .status(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
            .errorCode(ResponseStatus.INTERNAL_ERROR.getCode())
            .path(request.getRequestURI())
            .apiVersion("1.0")
            .build();

        ex.printStackTrace();
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private HttpStatus determineHttpStatus(ResponseStatus responseStatus) {
        return switch (responseStatus) {
            case NOT_FOUND -> HttpStatus.NOT_FOUND;
            case UNAUTHORIZED -> HttpStatus.UNAUTHORIZED;
            case FORBIDDEN -> HttpStatus.FORBIDDEN;
            case CONFLICT -> HttpStatus.CONFLICT;
            case VALIDATION_ERROR, BAD_REQUEST -> HttpStatus.BAD_REQUEST;
            default -> HttpStatus.INTERNAL_SERVER_ERROR;
        };
    }
}
