package com.stampcrush.backend.application.manager.sample.dto;

import com.stampcrush.backend.entity.sample.SampleBackImage;
import com.stampcrush.backend.entity.sample.SampleFrontImage;
import com.stampcrush.backend.entity.sample.SampleStampCoordinate;
import com.stampcrush.backend.entity.sample.SampleStampImage;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
//@EqualsAndHashCode
@RequiredArgsConstructor
public class SampleCouponsFindResultDto {

    private final List<SampleFrontImageDto> sampleFrontImages;
    private final List<SampleBackImageDto> sampleBackImages;
    private final List<SampleStampCoordinateDto> sampleStampCoordinates;
    private final List<SampleStampImageDto> sampleStampImages;

    public static SampleCouponsFindResultDto of(
            List<SampleFrontImage> sampleFrontImages,
            List<SampleBackImage> sampleBackImages,
            List<SampleStampCoordinate> sampleStampCoordinates,
            List<SampleStampImage> sampleStampImages
    ) {
        List<SampleFrontImageDto> sampleFrontImageDto = sampleFrontImages.stream().map(SampleFrontImageDto::from).toList();
        List<SampleBackImageDto> sampleBackImageDto = sampleBackImages.stream().map(SampleBackImageDto::from).toList();
        List<SampleStampCoordinateDto> sampleStampCoordinateDtos = sampleStampCoordinates.stream()
                .map(SampleStampCoordinateDto::from)
                .toList();
        List<SampleStampImageDto> sampleStampImageDto = sampleStampImages.stream().map(SampleStampImageDto::from).toList();

        return new SampleCouponsFindResultDto(sampleFrontImageDto, sampleBackImageDto, sampleStampCoordinateDtos, sampleStampImageDto);
    }

    @Getter
    @EqualsAndHashCode
    @RequiredArgsConstructor
    public static class SampleFrontImageDto {

        private final Long id;
        private final String imageUrl;

        public static SampleFrontImageDto from(SampleFrontImage sampleFrontImage) {
            return new SampleFrontImageDto(
                    sampleFrontImage.getId(),
                    sampleFrontImage.getImageUrl()
            );
        }
    }

    @Getter
    @EqualsAndHashCode
    @RequiredArgsConstructor
    public static class SampleBackImageDto {

        private final Long id;
        private final String imageUrl;

        public static SampleBackImageDto from(SampleBackImage sampleBackImage) {
            return new SampleBackImageDto(
                    sampleBackImage.getId(),
                    sampleBackImage.getImageUrl()
            );
        }
    }

    @Getter
    @EqualsAndHashCode
    @RequiredArgsConstructor
    public static class SampleStampCoordinateDto {

        private final Integer order;
        private final Integer xCoordinate;
        private final Integer yCoordinate;
        private final Long sampleBackImageId;

        public static SampleStampCoordinateDto from(SampleStampCoordinate coordinate) {
            return new SampleStampCoordinateDto(
                    coordinate.getStampOrder(),
                    coordinate.getXCoordinate(),
                    coordinate.getYCoordinate(),
                    coordinate.getSampleBackImage().getId()
            );
        }

        public boolean isCorrespondingCoordinateSampleBackImage(Long sampleBackImageId) {
            return this.getSampleBackImageId().equals(sampleBackImageId);
        }
    }

    @Getter
    @EqualsAndHashCode
    @RequiredArgsConstructor
    public static class SampleStampImageDto {

        private final Long id;
        private final String imageUrl;

        public static SampleStampImageDto from(SampleStampImage sampleStampImage) {
            return new SampleStampImageDto(
                    sampleStampImage.getId(),
                    sampleStampImage.getImageUrl()
            );
        }
    }
}
