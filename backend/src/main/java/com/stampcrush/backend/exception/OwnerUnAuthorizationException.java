package com.stampcrush.backend.exception;

public class OwnerUnAuthorizationException extends UnAuthorizationException {

    public OwnerUnAuthorizationException(String message) {
        super(message);
    }

    public OwnerUnAuthorizationException(Throwable cause) {
        super(cause);
    }

    public OwnerUnAuthorizationException(String message, Throwable cause) {
        super(message, cause);
    }
}
