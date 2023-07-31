package com.stampcrush.backend.application.coupon.dto;

import com.stampcrush.backend.entity.cafe.Cafe;
import com.stampcrush.backend.entity.coupon.Coupon;
import com.stampcrush.backend.entity.coupon.CouponStampCoordinate;
import com.stampcrush.backend.entity.coupon.CouponStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class CustomerCouponFindResultDto {

    private final CafeInfoDto cafeInfoDto;
    private final CouponInfoDto couponInfoDto;

    public static CustomerCouponFindResultDto of(
            Cafe cafe,
            Coupon coupon,
            Boolean isFavorites,
            List<CouponStampCoordinate> coordinates
    ) {
        CafeInfoDto cafeInfoDto = CafeInfoDto.from(cafe);
        CouponInfoDto couponInfoDto = CouponInfoDto.of(coupon, isFavorites, coordinates);
        return new CustomerCouponFindResultDto(cafeInfoDto, couponInfoDto);
    }

    @Getter
    @RequiredArgsConstructor
    public static class CafeInfoDto {
        private final Long id;
        private final String name;

        public static CafeInfoDto from(Cafe cafe) {
            return new CafeInfoDto(cafe.getId(), cafe.getName());
        }
    }

    @Getter
    @RequiredArgsConstructor
    public static class CouponInfoDto {
        private final Long id;
        private final Boolean isFavorites;
        private final CouponStatus status;
        private final Integer stampCount;
        private final Integer maxStampCount;
        private final String rewardName;
        private final String frontImageUrl;
        private final String backImageUrl;
        private final String stampImageUrl;
        private final List<CouponCoordinatesDto> coordinates;

        public static CouponInfoDto of(Coupon coupon, Boolean isFavorites, List<CouponStampCoordinate> coordinates) {
            return new CouponInfoDto(
                    coupon.getId(),
                    isFavorites,
                    coupon.getStatus(),
                    coupon.getStampCount(),
                    coupon.getCouponMaxStampCount(),
                    coupon.getRewardName(),
                    coupon.getCouponDesign().getFrontImageUrl(),
                    coupon.getCouponDesign().getBackImageUrl(),
                    coupon.getCouponDesign().getStampImageUrl(),
                    coordinates.stream().map(CouponCoordinatesDto::from).toList()
            );
        }

        @Getter
        @RequiredArgsConstructor
        public static class CouponCoordinatesDto {

            private final Integer order;
            private final Integer xCoordinate;
            private final Integer yCoordinate;

            public static CouponCoordinatesDto from(CouponStampCoordinate coordinate) {
                return new CouponCoordinatesDto(
                        coordinate.getStampOrder(),
                        coordinate.getXCoordinate(),
                        coordinate.getYCoordinate()
                );
            }
        }
    }
}
