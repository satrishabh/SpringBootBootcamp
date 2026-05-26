package com.techacademy.trainbase.exception;

import com.techacademy.trainbase.response.ResponseStatus;

public class BusinessException extends RuntimeException {

    private final ResponseStatus responseStatus;
    private final String errorCode;

    public BusinessException(ResponseStatus responseStatus) {
        super(responseStatus.getMessage());
        this.responseStatus = responseStatus;
        this.errorCode = responseStatus.getCode();
    }

    public BusinessException(ResponseStatus responseStatus, String message) {
        super(message);
        this.responseStatus = responseStatus;
        this.errorCode = responseStatus.getCode();
    }

    public BusinessException(String errorCode, String message) {
        super(message);
        this.responseStatus = ResponseStatus.INTERNAL_ERROR;
        this.errorCode = errorCode;
    }

    public ResponseStatus getResponseStatus() {
        return responseStatus;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
