package com.stampcrush.backend.exception;

public class ImageUploadFailException extends StampCrushException {
    public ImageUploadFailException(String message) {
        super(message);
    }

    public ImageUploadFailException(Throwable cause) {
        super(cause);
    }

    public ImageUploadFailException(String message, Throwable cause) {
        super(message, cause);
    }
}
