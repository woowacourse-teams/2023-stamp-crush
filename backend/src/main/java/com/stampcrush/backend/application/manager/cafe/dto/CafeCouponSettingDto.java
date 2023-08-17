package com.stampcrush.backend.application.manager.cafe.dto;

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

    public static CafeCouponSettingDto of(
            String frontImageUrl,
            String backImageUrl,
            String stampImageUrl,
            List<List<Integer>> coordinates,
            Integer maxStampCount,
            String reward,
            Integer expirePeriod
    ) {
        CafeCouponDesignDto cafeCouponDesignDto = CafeCouponDesignDto.of(
                frontImageUrl,
                backImageUrl,
                stampImageUrl,
                coordinates
        );
        CafePolicyDto cafePolicyDto = CafePolicyDto.of(
                maxStampCount,
                reward,
                expirePeriod
        );
        return new CafeCouponSettingDto(cafeCouponDesignDto, cafePolicyDto);
    }

    @Getter
    @ToString
    @RequiredArgsConstructor
    public static class CafeCouponDesignDto {

        private final String frontImageUrl;
        private final String backImageUrl;
        private final String stampImageUrl;
        private final List<CafeStampCoordinateDto> coordinates;

        public static CafeCouponDesignDto of(String frontImageUrl, String backImageUrl, String stampImageUrl, List<List<Integer>> coordinates) {
            return new CafeCouponDesignDto(
                    frontImageUrl,
                    backImageUrl,
                    stampImageUrl,
                    coordinates.stream().map(it -> new CafeStampCoordinateDto(it.get(0), it.get(1), it.get(2))).toList()
            );
        }

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

        public static CafePolicyDto of(Integer maxStampCount, String reward, Integer expirePeriod) {
            return new CafePolicyDto(maxStampCount, reward, expirePeriod);
        }
    }
}
