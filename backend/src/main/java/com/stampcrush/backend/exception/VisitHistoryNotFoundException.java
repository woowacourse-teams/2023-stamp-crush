package com.stampcrush.backend.exception;

public class VisitHistoryNotFoundException extends NotFoundException {

    public VisitHistoryNotFoundException(String message) {
        super(message);
    }

    public VisitHistoryNotFoundException(Throwable cause) {
        super(cause);
    }

    public VisitHistoryNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
