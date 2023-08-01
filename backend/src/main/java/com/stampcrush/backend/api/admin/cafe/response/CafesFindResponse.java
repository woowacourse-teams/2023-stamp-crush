package com.stampcrush.backend.api.admin.cafe.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class CafesFindResponse {

    private final List<CafeFindResponse> cafes;
}
