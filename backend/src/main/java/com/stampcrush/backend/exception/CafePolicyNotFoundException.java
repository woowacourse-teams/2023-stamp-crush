package com.stampcrush.backend.exception;

public class CafePolicyNotFoundException extends NotFoundException {

    public CafePolicyNotFoundException(String message) {
        super(message);
    }

    public CafePolicyNotFoundException(Throwable cause) {
        super(cause);
    }

    public CafePolicyNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
