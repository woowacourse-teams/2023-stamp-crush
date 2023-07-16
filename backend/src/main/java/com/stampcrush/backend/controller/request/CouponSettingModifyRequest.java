package com.stampcrush.backend.controller.request;

import com.stampcrush.backend.service.dto.CouponSettingDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class CouponSettingModifyRequest {

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

        public CouponSettingDto.CafeCouponDesignDto.CafeStampCoordinateDto toCafeStampCoordinateDto() {
            return new CouponSettingDto.CafeCouponDesignDto.CafeStampCoordinateDto(order, xCoordinate, yCoordinate);
        }
    }

    public CouponSettingDto toCouponSettingDto() {
        CouponSettingDto.CafeCouponDesignDto cafeCouponDesignDto = new CouponSettingDto.CafeCouponDesignDto(
                frontImageUrl,
                backImageUrl,
                stampImageUrl,
                coordinates.stream()
                        .map(CouponStampCoordinateRequest::toCafeStampCoordinateDto)
                        .toList()
        );

        CouponSettingDto.CafePolicyDto cafePolicyDto = new CouponSettingDto.CafePolicyDto(
                coordinates.size(),
                reward,
                expirePeriod
        );

        return new CouponSettingDto(cafeCouponDesignDto, cafePolicyDto);
    }
}
