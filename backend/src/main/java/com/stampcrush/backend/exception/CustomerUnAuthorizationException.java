package com.stampcrush.backend.exception;

public class CustomerUnAuthorizationException extends UnAuthorizationException {

    public CustomerUnAuthorizationException(String message) {
        super(message);
    }

    public CustomerUnAuthorizationException(Throwable cause) {
        super(cause);
    }

    public CustomerUnAuthorizationException(String message, Throwable cause) {
        super(message, cause);
    }
}
