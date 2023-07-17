package com.stampcrush.backend.controller.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SampleCouponQueryRequest {

    private final Integer maxStampCount;
}
