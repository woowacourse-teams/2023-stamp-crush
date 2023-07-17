package com.stampcrush.backend.api.sample.response;

import com.stampcrush.backend.application.sample.dto.SampleCouponsDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class SampleCouponFindResponse {

    private final List<SampleFrontImageFindResponse> sampleFrontImages;
    private final List<SampleBackImageFindResponse> sampleBackImages;
    private final List<SampleStampImageFindResponse> sampleStampImages;

    public static SampleCouponFindResponse from(SampleCouponsDto sampleCoupons) {
        List<SampleFrontImageFindResponse> sampleFrontImages = sampleCoupons.getSampleFrontImages()
                .stream()
                .map(sampleFrontImage -> new SampleFrontImageFindResponse(
                        sampleFrontImage.getId(),
                        sampleFrontImage.getImageUrl()
                ))
                .toList();

        List<SampleBackImageFindResponse> sampleBackImages = sampleCoupons.getSampleBackImages()
                .stream()
                .map(sampleBackImage -> new SampleBackImageFindResponse(
                        sampleBackImage.getId(),
                        sampleBackImage.getImageUrl(),
                        sampleCoupons.getSampleStampCoordinates().stream().filter(
                                        sampleStampCoordinate -> sampleStampCoordinate.getSampleBackImage().equals(sampleBackImage))
                                .map(sampleStampCoordinate -> new SampleBackImageFindResponse.SampleStampCoordinateFindResponse(
                                        sampleStampCoordinate.getId(),
                                        sampleStampCoordinate.getStampOrder(),
                                        sampleStampCoordinate.getXCoordinate(),
                                        sampleStampCoordinate.getYCoordinate()
                                ))
                                .toList()
                )).toList();

        List<SampleStampImageFindResponse> sampleStampImages = sampleCoupons.getSampleStampImages()
                .stream()
                .map(sampleStampImage -> new SampleStampImageFindResponse(
                        sampleStampImage.getId(),
                        sampleStampImage.getImageUrl()
                ))
                .toList();

        return new SampleCouponFindResponse(sampleFrontImages, sampleBackImages, sampleStampImages);
    }

    @Getter
    @RequiredArgsConstructor
    public static class SampleFrontImageFindResponse {

        private final Long id;
        private final String imageUrl;
    }

    @Getter
    @RequiredArgsConstructor
    public static class SampleBackImageFindResponse {

        private final Long id;
        private final String imageUrl;
        private final List<SampleStampCoordinateFindResponse> stampCoordinates;

        @Getter
        @RequiredArgsConstructor
        public static class SampleStampCoordinateFindResponse {

            private final Long id;
            private final Integer stampOrder;
            private final Integer xCoordinate;
            private final Integer yCoordinate;
        }
    }

    @Getter
    @RequiredArgsConstructor
    public static class SampleStampImageFindResponse {

        private final Long id;
        private final String imageUrl;
    }
}
