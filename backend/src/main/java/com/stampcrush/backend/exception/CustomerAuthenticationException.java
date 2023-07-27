package com.stampcrush.backend.exception;

public class CustomerAuthenticationException extends AuthenticationException {

    public CustomerAuthenticationException(String message) {
        super(message);
    }

    public CustomerAuthenticationException(Throwable cause) {
        super(cause);
    }

    public CustomerAuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }
}
