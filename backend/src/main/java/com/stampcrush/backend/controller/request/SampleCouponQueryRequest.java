package com.stampcrush.backend.controller.request;

import lombok.Getter;

@Getter
public class SampleCouponQueryRequest {

    private Integer maxStampCount;

    public SampleCouponQueryRequest(Integer maxStampCount) {
        this.maxStampCount = maxStampCount;
    }
}
