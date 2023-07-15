package com.stampcrush.backend.controller.response;

import com.stampcrush.backend.service.dto.SampleCouponsDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class SampleCouponQueryResponse {

    private final List<SampleFrontImageDto> sampleFrontImages;
    private final List<SampleBackImageDto> sampleBackImages;
    private final List<SampleStampImageDto> sampleStampImages;

    public static SampleCouponQueryResponse from(SampleCouponsDto sampleCoupons) {
        List<SampleFrontImageDto> sampleFrontImages = sampleCoupons.getSampleFrontImages()
                .stream()
                .map(sampleFrontImage -> new SampleFrontImageDto(
                        sampleFrontImage.getId(),
                        sampleFrontImage.getImageUrl()
                ))
                .toList();

        List<SampleBackImageDto> sampleBackImages = sampleCoupons.getSampleBackImages()
                .stream()
                .map(sampleBackImage -> new SampleBackImageDto(
                        sampleBackImage.getId(),
                        sampleBackImage.getImageUrl(),
                        sampleCoupons.getSampleStampCoordinates().stream().filter(
                                        sampleStampCoordinate -> sampleStampCoordinate.getSampleBackImage().equals(sampleBackImage))
                                .map(sampleStampCoordinate -> new SampleStampCoordinateDto(
                                        sampleStampCoordinate.getId(),
                                        sampleStampCoordinate.getStampOrder(),
                                        sampleStampCoordinate.getXCoordinate(),
                                        sampleStampCoordinate.getYCoordinate()
                                ))
                                .toList()
                )).toList();

        List<SampleStampImageDto> sampleStampImages = sampleCoupons.getSampleStampImages()
                .stream()
                .map(sampleStampImage -> new SampleStampImageDto(
                        sampleStampImage.getId(),
                        sampleStampImage.getImageUrl()
                ))
                .toList();

        return new SampleCouponQueryResponse(sampleFrontImages, sampleBackImages, sampleStampImages);
    }
}
