package com.stampcrush.backend.api.sample.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@NoArgsConstructor
public class SampleCouponFindRequest {

    @JsonProperty("max-stamp-count")
    private Integer maxStampCount;
}
