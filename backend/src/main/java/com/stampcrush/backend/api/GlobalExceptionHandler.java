package com.stampcrush.backend.api;

import com.stampcrush.backend.exception.BadRequestException;
import com.stampcrush.backend.exception.ForbiddenException;
import com.stampcrush.backend.exception.NotFoundException;
import com.stampcrush.backend.exception.StampCrushException;
import com.stampcrush.backend.exception.UnAuthorizationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Slf4j
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
        log.warn("NotFoundException | " + exception.getMessage());
        return ResponseEntity
                .status(NOT_FOUND.value())
                .body(new ExceptionResponse(exception.getMessage()));
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ExceptionResponse> handleBadRequestException(BadRequestException exception) {
        log.warn("BadRequestException | " + exception.getMessage());
        return ResponseEntity
                .status(BAD_REQUEST.value())
                .body(new ExceptionResponse(exception.getMessage()));
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ExceptionResponse> handleForbiddenException(ForbiddenException exception) {
        log.warn("ForbiddenException | " + exception.getMessage());
        return ResponseEntity
                .status(FORBIDDEN.value())
                .body(new ExceptionResponse(exception.getMessage()));
    }

    @ExceptionHandler(UnAuthorizationException.class)
    public ResponseEntity<ExceptionResponse> handleUnAuthorizationException(UnAuthorizationException exception) {
        log.warn("UnAuthorizationException | " + exception.getMessage());
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED.value())
                .body(new ExceptionResponse(exception.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleException(Exception exception) {
        return ResponseEntity
                .status(INTERNAL_SERVER_ERROR.value())
                .body(new ExceptionResponse(exception.getMessage()));
//                .body(new ExceptionResponse("알 수 없는 에러가 발생했습니다. 잠시 후 다시 시도해 주세요."));
    }
}
