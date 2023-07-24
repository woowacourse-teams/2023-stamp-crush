package com.stampcrush.backend.entity.coupon;

import com.stampcrush.backend.application.coupon.CustomerCouponStatistics;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
public class Coupons {

    private final List<Coupon> coupons;

    public CustomerCouponStatistics calculateStatistics() {
        int stampCount = 0;
        int rewardCount = 0;
        int visitCount = 0;
        int maxStampCount = 0;
        LocalDateTime firstVisitDate = LocalDateTime.MAX;

        for (Coupon coupon : coupons) {
            stampCount = calculateCurrentStampWhenUsingCoupon(stampCount, coupon);
            rewardCount += addRewardCouponCount(coupon);
            visitCount += coupon.calculateVisitCount();
            firstVisitDate = coupon.compareCreatedAtAndReturnEarlier(firstVisitDate);
            maxStampCount = coupon.calculateMaxStampCountWhenAccumulating();
        }
        return new CustomerCouponStatistics(stampCount, rewardCount, visitCount, maxStampCount, firstVisitDate);
    }

    private int calculateCurrentStampWhenUsingCoupon(int stampCount, Coupon coupon) {
        if (coupon.isAccumulating()) {
            return coupon.getStampCount();
        }
        return stampCount;
    }

    private int addRewardCouponCount(Coupon coupon) {
        if (coupon.isRewarded()) {
            return 1;
        }
        return 0;
    }
}
