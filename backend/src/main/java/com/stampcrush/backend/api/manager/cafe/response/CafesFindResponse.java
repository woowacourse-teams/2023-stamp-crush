package com.stampcrush.backend.api.manager.cafe.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class CafesFindResponse {

    private final List<CafeFindResponse> cafes;
}
