package com.stampcrush.backend.api.sample.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class SampleCouponFindRequest {

    @JsonProperty("max-stamp-count")
    private Integer maxStampCount;

    public SampleCouponFindRequest() {
    }

    public SampleCouponFindRequest(Integer maxStampCount) {
        this.maxStampCount = maxStampCount;
    }
}
