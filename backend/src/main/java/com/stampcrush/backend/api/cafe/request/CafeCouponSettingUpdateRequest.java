package com.stampcrush.backend.api.cafe.request;

import com.stampcrush.backend.application.cafe.dto.CafeCouponSettingDto;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class CafeCouponSettingUpdateRequest {

    @NotNull
    @NotBlank
    private final String frontImageUrl;

    @NotNull
    @NotBlank
    private final String backImageUrl;

    @NotNull
    @NotBlank
    private final String stampImageUrl;

    @NotEmpty
    private final List<CouponStampCoordinateRequest> coordinates;

    @NotNull
    @NotBlank
    private final String reward;

    @NotNull
    @Positive
    private final Integer expirePeriod;

    @Getter
    @RequiredArgsConstructor
    public static class CouponStampCoordinateRequest {

        @NotNull
        @Positive
        private final Integer order;

        @NotNull
        @PositiveOrZero
        private final Integer xCoordinate;

        @NotNull
        @PositiveOrZero
        private final Integer yCoordinate;

        public CafeCouponSettingDto.CafeCouponDesignDto.CafeStampCoordinateDto toCafeStampCoordinateDto() {
            return new CafeCouponSettingDto.CafeCouponDesignDto.CafeStampCoordinateDto(order, xCoordinate, yCoordinate);
        }
    }

    public CafeCouponSettingDto toCouponSettingDto() {
        CafeCouponSettingDto.CafeCouponDesignDto cafeCouponDesignDto = new CafeCouponSettingDto.CafeCouponDesignDto(
                frontImageUrl,
                backImageUrl,
                stampImageUrl,
                coordinates.stream()
                        .map(CouponStampCoordinateRequest::toCafeStampCoordinateDto)
                        .toList()
        );

        CafeCouponSettingDto.CafePolicyDto cafePolicyDto = new CafeCouponSettingDto.CafePolicyDto(
                coordinates.size(),
                reward,
                expirePeriod
        );

        return new CafeCouponSettingDto(cafeCouponDesignDto, cafePolicyDto);
    }
}
