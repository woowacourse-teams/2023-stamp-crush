package com.stampcrush.backend.api.cafe.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CafeCreateRequest {

    private final String name;

    private final String roadAddress;

    private final String detailAddress;

    private final String businessRegistrationNumber;
}
