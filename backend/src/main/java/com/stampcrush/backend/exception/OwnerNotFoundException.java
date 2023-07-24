package com.stampcrush.backend.exception;

public class OwnerNotFoundException extends NotFoundException {

    public OwnerNotFoundException(String message) {
        super(message);
    }

    public OwnerNotFoundException(Throwable cause) {
        super(cause);
    }

    public OwnerNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
