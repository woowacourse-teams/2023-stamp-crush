package com.stampcrush.backend.controller.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class SampleCouponQueryResponse {

    private final List<SampleFrontImageDto> sampleFrontImages;
    private final List<SampleBackImageDto> sampleBackImages;
    private final List<SampleStampImageDto> sampleStampImages;
}
