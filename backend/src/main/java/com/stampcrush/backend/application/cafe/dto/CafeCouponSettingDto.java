package com.stampcrush.backend.application.cafe.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
@RequiredArgsConstructor
public class CafeCouponSettingDto {

    private final CafeCouponDesignDto cafeCouponDesignDto;
    private final CafePolicyDto cafePolicyDto;

    @Getter
    @ToString
    @RequiredArgsConstructor
    public static class CafeCouponDesignDto {

        private final String frontImageUrl;
        private final String backImageUrl;
        private final String stampImageUrl;
        private final List<CafeStampCoordinateDto> coordinates;
        private final List<Integer> tests;

        @Getter
        @ToString
        @RequiredArgsConstructor
        public static class CafeStampCoordinateDto {

            private final Integer order;
            private final Integer xCoordinate;
            private final Integer yCoordinate;
        }
    }

    @Getter
    @ToString
    @RequiredArgsConstructor
    public static class CafePolicyDto {

        private final Integer maxStampCount;
        private final String reward;
        private final Integer expirePeriod;
    }
}
