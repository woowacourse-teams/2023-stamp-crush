package com.stampcrush.backend.exception;

public class LoginIdBadRequestException extends BadRequestException {

    public LoginIdBadRequestException(String message) {
        super(message);
    }

    public LoginIdBadRequestException(Throwable cause) {
        super(cause);
    }

    public LoginIdBadRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
