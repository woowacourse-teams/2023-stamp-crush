package com.stampcrush.backend.exception;

public class ForbiddenException extends StampCrushException {

    public ForbiddenException(Throwable cause) {
        super(cause);
    }

    public ForbiddenException(String message, Throwable cause) {
        super(message, cause);
    }
}
