package com.stampcrush.backend.exception;

public class UnAuthorizationException extends StampCrushException {

    public UnAuthorizationException(Throwable cause) {
        super(cause);
    }

    public UnAuthorizationException(String message, Throwable cause) {
        super(message, cause);
    }
}
