package com.stampcrush.backend.exception;

public class CustomerBadRequestException extends BadRequestException {
    public CustomerBadRequestException(String message) {
        super(message);
    }

    public CustomerBadRequestException(Throwable cause) {
        super(cause);
    }

    public CustomerBadRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
