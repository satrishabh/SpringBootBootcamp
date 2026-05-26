package com.techacademy.trainbase.controller;

import com.techacademy.trainbase.response.ApiResponse;
import com.techacademy.trainbase.response.PaginationMetadata;
import com.techacademy.trainbase.response.ResponseStatus;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.List;

@RequestMapping("/api/v1")
public abstract class BaseController {

    protected String getRequestPath(HttpServletRequest request) {
        return request.getRequestURI();
    }

    protected String getApiVersion() {
        return "1.0";
    }

    protected <T> ResponseEntity<ApiResponse<T>> success(T data) {
        return ResponseEntity.ok(
            ApiResponse.<T>builder()
                .success(true)
                .message(ResponseStatus.SUCCESS.getMessage())
                .data(data)
                .timestamp(LocalDateTime.now())
                .statusCode(HttpStatus.OK.value())
                .status(HttpStatus.OK.getReasonPhrase())
                .apiVersion(getApiVersion())
                .build()
        );
    }

    protected <T> ResponseEntity<ApiResponse<T>> success(String message, T data) {
        return ResponseEntity.ok(
            ApiResponse.<T>builder()
                .success(true)
                .message(message)
                .data(data)
                .timestamp(LocalDateTime.now())
                .statusCode(HttpStatus.OK.value())
                .status(HttpStatus.OK.getReasonPhrase())
                .apiVersion(getApiVersion())
                .build()
        );
    }

    protected <T> ResponseEntity<ApiResponse<T>> created(T data) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ApiResponse.<T>builder()
                .success(true)
                .message(ResponseStatus.CREATED.getMessage())
                .data(data)
                .timestamp(LocalDateTime.now())
                .statusCode(HttpStatus.CREATED.value())
                .status(HttpStatus.CREATED.getReasonPhrase())
                .apiVersion(getApiVersion())
                .build());
    }

    protected <T> ResponseEntity<ApiResponse<T>> updated(T data) {
        return ResponseEntity.ok(
            ApiResponse.<T>builder()
                .success(true)
                .message(ResponseStatus.UPDATED.getMessage())
                .data(data)
                .timestamp(LocalDateTime.now())
                .statusCode(HttpStatus.OK.value())
                .status(HttpStatus.OK.getReasonPhrase())
                .apiVersion(getApiVersion())
                .build()
        );
    }

    protected ResponseEntity<ApiResponse<Void>> deleted() {
        return ResponseEntity.ok(
            ApiResponse.<Void>builder()
                .success(true)
                .message(ResponseStatus.DELETED.getMessage())
                .timestamp(LocalDateTime.now())
                .statusCode(HttpStatus.OK.value())
                .status(HttpStatus.OK.getReasonPhrase())
                .apiVersion(getApiVersion())
                .build()
        );
    }

    protected <T> ResponseEntity<ApiResponse<List<T>>> paginated(Page<T> page, HttpServletRequest request) {
        PaginationMetadata pagination = PaginationMetadata.of(
            page.getNumber(),
            page.getSize(),
            page.getTotalElements()
        );

        ApiResponse<List<T>> response = ApiResponse.<List<T>>builder()
            .success(true)
            .message(ResponseStatus.SUCCESS.getMessage())
            .data(page.getContent())
            .timestamp(LocalDateTime.now())
            .statusCode(HttpStatus.OK.value())
            .status(HttpStatus.OK.getReasonPhrase())
            .apiVersion(getApiVersion())
            .path(getRequestPath(request))
            .pagination(pagination)
            .build();

        return ResponseEntity.ok(response);
    }

    protected <T> ResponseEntity<ApiResponse<T>> error(HttpStatus status, String message, String errorCode) {
        return ResponseEntity.status(status)
            .body(ApiResponse.<T>builder()
                .success(false)
                .message(message)
                .timestamp(LocalDateTime.now())
                .statusCode(status.value())
                .status(status.getReasonPhrase())
                .errorCode(errorCode)
                .apiVersion(getApiVersion())
                .build());
    }

    protected <T> ResponseEntity<ApiResponse<T>> notFound(String resource, Object id) {
        return error(HttpStatus.NOT_FOUND,
            String.format("%s with id %s not found", resource, id),
            ResponseStatus.NOT_FOUND.getCode());
    }

    protected <T> ResponseEntity<ApiResponse<T>> validationError(String message, String errorCode) {
        return error(HttpStatus.BAD_REQUEST, message, errorCode);
    }
}
