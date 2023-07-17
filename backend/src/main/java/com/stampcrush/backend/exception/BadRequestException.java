package com.stampcrush.backend.exception;

public class BadRequestException extends StampCrushException {

    public BadRequestException(Throwable cause) {
        super(cause);
    }

    public BadRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
