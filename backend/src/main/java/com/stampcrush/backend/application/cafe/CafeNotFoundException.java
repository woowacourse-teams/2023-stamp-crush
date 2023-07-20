package com.stampcrush.backend.application.cafe;

import com.stampcrush.backend.exception.NotFoundException;

public class CafeNotFoundException extends NotFoundException {

    public CafeNotFoundException(Throwable cause) {
        super(cause);
    }

    public CafeNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
