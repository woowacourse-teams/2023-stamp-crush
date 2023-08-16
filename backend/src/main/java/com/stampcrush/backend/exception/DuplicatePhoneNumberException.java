package com.stampcrush.backend.exception;

public class DuplicatePhoneNumberException extends BadRequestException {

    public DuplicatePhoneNumberException(String message) {
        super(message);
    }

    public DuplicatePhoneNumberException(Throwable cause) {
        super(cause);
    }

    public DuplicatePhoneNumberException(String message, Throwable cause) {
        super(message, cause);
    }
}
