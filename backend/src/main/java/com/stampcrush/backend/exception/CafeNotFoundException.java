package com.stampcrush.backend.exception;

public class CafeNotFoundException extends NotFoundException {

    public CafeNotFoundException(String message) {
        super(message);
    }

    public CafeNotFoundException(Throwable cause) {
        super(cause);
    }

    public CafeNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
