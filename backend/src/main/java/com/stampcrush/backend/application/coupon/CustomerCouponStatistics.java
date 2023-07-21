package com.stampcrush.backend.application.coupon;

import com.stampcrush.backend.entity.coupon.Coupon;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class CustomerCouponStatistics {

    private final int stampCount;
    private final int rewardCount;
    private final int visitCount;
    private final LocalDateTime firstVisitDate;

    public static CustomerCouponStatistics produceFrom(List<Coupon> coupons) {
        int stampCount = 0;
        int rewardCount = 0;
        int visitCount = 0;
        LocalDateTime firstVisitDate = LocalDateTime.MAX;
        for (Coupon coupon : coupons) {
            stampCount = calculateCurrentStampWhenUsingCoupon(stampCount, coupon);
            rewardCount += addRewardCouponCount(coupon);
            visitCount += coupon.calculateVisitCount();
            firstVisitDate = coupon.compareCreatedAtAndReturnEarlier(firstVisitDate);
        }
        return new CustomerCouponStatistics(stampCount, rewardCount, visitCount, firstVisitDate);
    }

    private static int calculateCurrentStampWhenUsingCoupon(int stampCount, Coupon coupon) {
        if (coupon.isUsing()) {
            stampCount = coupon.getStampCount();
        }
        return stampCount;
    }

    private static int addRewardCouponCount(Coupon coupon) {
        if (coupon.isRewarded()) {
            return 1;
        }
        return 0;
    }
}
