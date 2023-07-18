package com.stampcrush.backend.api.cafe.request;

import com.stampcrush.backend.application.cafe.dto.CafeCouponSettingDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class CafeCouponSettingUpdateRequest {

    private final String frontImageUrl;
    private final String backImageUrl;
    private final String stampImageUrl;
    private final List<CouponStampCoordinateRequest> coordinates;
    private final String reward;
    private final Integer expirePeriod;

    @Getter
    @RequiredArgsConstructor
    public static class CouponStampCoordinateRequest {

        private final Integer order;
        private final Integer xCoordinate;
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
