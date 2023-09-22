package com.stampcrush.backend.api.manager.cafe.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class CafesFindResponse {

    private List<CafeFindResponse> cafes;
}
