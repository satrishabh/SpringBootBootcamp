package com.techacademy.trainbase.exception;

import com.techacademy.trainbase.response.ResponseStatus;

public class UserAlreadyExistsException extends BusinessException {

    public UserAlreadyExistsException(String message) {
        super(ResponseStatus.USER_EXISTS, message);
    }
}
