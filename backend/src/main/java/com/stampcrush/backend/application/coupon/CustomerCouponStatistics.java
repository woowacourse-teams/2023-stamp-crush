package com.stampcrush.backend.application.coupon;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class CustomerCouponStatistics {

    private final int stampCount;
    private final int rewardCount;
    private final int visitCount;
    private final int maxStampCount;
    private final LocalDateTime firstVisitDate;
}
