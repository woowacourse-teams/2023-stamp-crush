package com.stampcrush.backend.api.visitor.coupon.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.stampcrush.backend.application.visitor.coupon.dto.CustomerCouponFindResultDto;
import com.stampcrush.backend.entity.coupon.CouponStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Stream;

@Getter
@RequiredArgsConstructor
public class CustomerCouponFindResponse {

    private final CafeInfoResponse cafeInfo;
    private final List<CustomerCouponInfoResponse> couponInfos;

    public static CustomerCouponFindResponse from(CustomerCouponFindResultDto dto) {
        CafeInfoResponse cafeInfo = CafeInfoResponse.from(dto.getCafeInfoDto());
        List<CustomerCouponInfoResponse> couponInfos = Stream.of(dto.getCouponInfoDto())
                .map(CustomerCouponInfoResponse::from)
                .toList();
        return new CustomerCouponFindResponse(cafeInfo, couponInfos);
    }

    @Getter
    @RequiredArgsConstructor
    private static class CafeInfoResponse {

        private final Long id;
        private final String name;

        static CafeInfoResponse from(CustomerCouponFindResultDto.CafeInfoDto cafeInfoDto) {
            return new CafeInfoResponse(cafeInfoDto.getId(), cafeInfoDto.getName());
        }
    }

    @Getter
    @RequiredArgsConstructor
    private static class CustomerCouponInfoResponse {

        private final Long id;
        private final Boolean isFavorites;
        private final CouponStatus status;
        private final Integer stampCount;
        private final Integer maxStampCount;
        private final String rewardName;
        private final String frontImageUrl;
        private final String backImageUrl;
        private final String stampImageUrl;
        private final List<CoordinatesResponse> coordinates;

        static CustomerCouponInfoResponse from(CustomerCouponFindResultDto.CouponInfoDto couponInfoDto) {
            return new CustomerCouponInfoResponse(
                    couponInfoDto.getId(),
                    couponInfoDto.getIsFavorites(),
                    couponInfoDto.getStatus(),
                    couponInfoDto.getStampCount(),
                    couponInfoDto.getMaxStampCount(),
                    couponInfoDto.getRewardName(),
                    couponInfoDto.getFrontImageUrl(),
                    couponInfoDto.getBackImageUrl(),
                    couponInfoDto.getStampImageUrl(),
                    couponInfoDto.getCoordinates().stream().map(CoordinatesResponse::from).toList()
            );
        }

        @Getter
        @RequiredArgsConstructor
        private static class CoordinatesResponse {

            private final Integer order;

            private final int xCoordinate;
            private final int yCoordinate;

            static CoordinatesResponse from(CustomerCouponFindResultDto.CouponInfoDto.CouponCoordinatesDto couponCoordinatesDto) {
                return new CoordinatesResponse(
                        couponCoordinatesDto.getOrder(),
                        couponCoordinatesDto.getXCoordinate(),
                        couponCoordinatesDto.getYCoordinate()
                );
            }
        }
    }
}
