package com.techacademy.trainbase.exception;

import com.techacademy.trainbase.response.ResponseStatus;

public class InvalidCredentialsException extends BusinessException {

    public InvalidCredentialsException(String message) {
        super(ResponseStatus.INVALID_CREDENTIALS, message);
    }
}
