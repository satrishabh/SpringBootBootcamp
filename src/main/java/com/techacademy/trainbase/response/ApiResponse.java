package com.techacademy.trainbase.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {

    private boolean success;
    private String message;
    private T data;
    private LocalDateTime timestamp;
    private Integer statusCode;
    private String status;
    private String path;
    private String apiVersion;
    private String errorCode;
    private Map<String, String> validationErrors;
    private PaginationMetadata pagination;

    public static <T> ApiResponse<T> success(T data) {
        return ApiResponse.<T>builder()
            .success(true)
            .message(ResponseStatus.SUCCESS.getMessage())
            .data(data)
            .timestamp(LocalDateTime.now())
            .statusCode(HttpStatus.OK.value())
            .status(HttpStatus.OK.getReasonPhrase())
            .build();
    }

    public static <T> ApiResponse<T> success(String message, T data) {
        return ApiResponse.<T>builder()
            .success(true)
            .message(message)
            .data(data)
            .timestamp(LocalDateTime.now())
            .statusCode(HttpStatus.OK.value())
            .status(HttpStatus.OK.getReasonPhrase())
            .build();
    }

    public static <T> ApiResponse<T> created(T data) {
        return ApiResponse.<T>builder()
            .success(true)
            .message(ResponseStatus.CREATED.getMessage())
            .data(data)
            .timestamp(LocalDateTime.now())
            .statusCode(HttpStatus.CREATED.value())
            .status(HttpStatus.CREATED.getReasonPhrase())
            .build();
    }

    public static <T> ApiResponse<T> error(String message) {
        return ApiResponse.<T>builder()
            .success(false)
            .message(message)
            .timestamp(LocalDateTime.now())
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .status(HttpStatus.BAD_REQUEST.getReasonPhrase())
            .build();
    }

    public static <T> ApiResponse<T> error(HttpStatus status, String message) {
        return ApiResponse.<T>builder()
            .success(false)
            .message(message)
            .timestamp(LocalDateTime.now())
            .statusCode(status.value())
            .status(status.getReasonPhrase())
            .build();
    }

    public static <T> ApiResponse<T> error(HttpStatus status, String message, String errorCode) {
        return ApiResponse.<T>builder()
            .success(false)
            .message(message)
            .timestamp(LocalDateTime.now())
            .statusCode(status.value())
            .status(status.getReasonPhrase())
            .errorCode(errorCode)
            .build();
    }
}
