package com.stampcrush.backend.api.sample.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SampleCouponFindRequest {

    private final Integer maxStampCount;
}
