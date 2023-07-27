package com.stampcrush.backend.exception;

public class OwnerAuthenticationException extends AuthenticationException {

    public OwnerAuthenticationException(String message) {
        super(message);
    }

    public OwnerAuthenticationException(Throwable cause) {
        super(cause);
    }

    public OwnerAuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }
}
