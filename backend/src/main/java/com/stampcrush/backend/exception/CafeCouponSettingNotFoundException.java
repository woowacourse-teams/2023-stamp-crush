package com.stampcrush.backend.exception;

public class CafeCouponSettingNotFoundException extends NotFoundException {

    public CafeCouponSettingNotFoundException(String message) {
        super(message);
    }

    public CafeCouponSettingNotFoundException(Throwable cause) {
        super(cause);
    }

    public CafeCouponSettingNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
