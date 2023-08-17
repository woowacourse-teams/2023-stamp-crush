package com.stampcrush.backend.exception;

public class CouponNotFoundException extends NotFoundException {

    public CouponNotFoundException(String message) {
        super(message);
    }

    public CouponNotFoundException(Throwable cause) {
        super(cause);
    }

    public CouponNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
