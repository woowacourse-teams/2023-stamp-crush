package com.stampcrush.backend.api.manager.sample.response;

import com.stampcrush.backend.application.manager.sample.dto.SampleCouponsFindResultDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class SampleCouponFindResponse {

    private final List<SampleFrontImageFindResponse> sampleFrontImages;
    private final List<SampleBackImageFindResponse> sampleBackImages;
    private final List<SampleStampImageFindResponse> sampleStampImages;

    public static SampleCouponFindResponse from(SampleCouponsFindResultDto sampleCoupons) {
        List<SampleFrontImageFindResponse> sampleFrontImages = sampleCoupons.getSampleFrontImages()
                .stream()
                .map(SampleFrontImageFindResponse::from)
                .toList();

        List<SampleCouponsFindResultDto.SampleBackImageDto> backImages = sampleCoupons.getSampleBackImages();
        List<SampleCouponsFindResultDto.SampleStampCoordinateDto> coordinates = sampleCoupons.getSampleStampCoordinates();

        List<SampleBackImageFindResponse> sampleBackImages = backImages
                .stream()
                .map(it -> new SampleBackImageFindResponse(
                        it.getId(),
                        it.getImageUrl(),
                        coordinates.stream()
                                .filter(coordinate -> coordinate.isCorrespondingCoordinateSampleBackImage(it.getId()))
                                .map(coordinate -> new SampleBackImageFindResponse.SampleStampCoordinateFindResponse(
                                        coordinate.getOrder(),
                                        coordinate.getXCoordinate(),
                                        coordinate.getYCoordinate()
                                ))
                                .toList()
                ))
                .toList();

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

        public static SampleFrontImageFindResponse from(SampleCouponsFindResultDto.SampleFrontImageDto sampleFrontImage) {
            return new SampleFrontImageFindResponse(sampleFrontImage.getId(), sampleFrontImage.getImageUrl());
        }
    }

    @Getter
    @RequiredArgsConstructor
    public static class SampleBackImageFindResponse {

        private final Long id;
        private final String imageUrl;
        private final List<SampleStampCoordinateFindResponse> stampCoordinates;

        @RequiredArgsConstructor
        public static class SampleStampCoordinateFindResponse {

            private final Integer order;
            private final Integer xCoordinate;
            private final Integer yCoordinate;

            public Integer getOrder() {
                return order;
            }

            public Integer getxCoordinate() {
                return xCoordinate;
            }

            public Integer getyCoordinate() {
                return yCoordinate;
            }
        }
    }

    @Getter
    @RequiredArgsConstructor
    public static class SampleStampImageFindResponse {

        private final Long id;
        private final String imageUrl;
    }
}
