package com.stampcrush.backend.exception;

public class StampCrushException extends RuntimeException {

    public StampCrushException(String message) {
        super(message);
    }

    public StampCrushException(Throwable cause) {
        super(cause);
    }

    public StampCrushException(String message, Throwable cause) {
        super(message, cause);
    }
}
