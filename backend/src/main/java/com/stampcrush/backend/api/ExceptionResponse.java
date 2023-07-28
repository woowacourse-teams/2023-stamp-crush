package com.stampcrush.backend.api;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ExceptionResponse {

    private final String exceptionMessage;
}
