package com.stampcrush.backend.exception;

public class NotFoundException extends StampCrushException {

    public NotFoundException(Throwable cause) {
        super(cause);
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
