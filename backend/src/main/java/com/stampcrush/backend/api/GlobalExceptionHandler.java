package com.stampcrush.backend.api;

import com.stampcrush.backend.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(StampCrushException.class)
    public ResponseEntity<ExceptionResponse> handleStampCrushException(StampCrushException exception) {
        return ResponseEntity
                .status(INTERNAL_SERVER_ERROR.value())
                .body(new ExceptionResponse(exception.getMessage()));
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleNotFoundException(NotFoundException exception) {
        return ResponseEntity
                .status(NOT_FOUND.value())
                .body(new ExceptionResponse(exception.getMessage()));
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ExceptionResponse> handleBadRequestException(BadRequestException exception) {
        return ResponseEntity
                .status(BAD_REQUEST.value())
                .body(new ExceptionResponse(exception.getMessage()));
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ExceptionResponse> handleForbiddenException(ForbiddenException exception) {
        return ResponseEntity
                .status(FORBIDDEN.value())
                .body(new ExceptionResponse(exception.getMessage()));
    }

    @ExceptionHandler(UnAuthorizationException.class)
    public ResponseEntity<ExceptionResponse> handleUnAuthorizationException(UnAuthorizationException exception) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED.value())
                .body(new ExceptionResponse(exception.getMessage()));
    }

//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<ExceptionResponse> handleException(Exception exception) {
//        return ResponseEntity
//                .status(INTERNAL_SERVER_ERROR.value())
//                .body(new ExceptionResponse("알 수 없는 에러가 발생했습니다. 잠시 후 다시 시도해 주세요."));
//    }
}
