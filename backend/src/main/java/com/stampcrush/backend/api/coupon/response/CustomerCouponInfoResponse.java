package com.stampcrush.backend.api.coupon.response;

import com.stampcrush.backend.entity.coupon.CouponStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class CustomerCouponInfoResponse {

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

    @Getter
    @RequiredArgsConstructor
    private class CoordinatesResponse {

        private final Integer order;
        private final Integer xCoordinate;
        private final Integer yCoordinate;
    }
}
