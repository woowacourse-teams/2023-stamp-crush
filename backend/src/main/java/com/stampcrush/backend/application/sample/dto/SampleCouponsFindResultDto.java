package com.stampcrush.backend.application.sample.dto;

import com.stampcrush.backend.entity.sample.SampleBackImage;
import com.stampcrush.backend.entity.sample.SampleFrontImage;
import com.stampcrush.backend.entity.sample.SampleStampCoordinate;
import com.stampcrush.backend.entity.sample.SampleStampImage;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class SampleCouponsFindResultDto {

    private final List<SampleFrontImage> sampleFrontImages;
    private final List<SampleBackImage> sampleBackImages;
    private final List<SampleStampCoordinate> sampleStampCoordinates;
    private final List<SampleStampImage> sampleStampImages;
}
