package com.stampcrush.backend.application.manager.coupon;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CustomerCouponStatistics {

    private final int stampCount;
    private final int maxStampCount;
}
