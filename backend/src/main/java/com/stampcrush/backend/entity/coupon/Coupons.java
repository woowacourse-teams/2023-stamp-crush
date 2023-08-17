package com.stampcrush.backend.entity.coupon;

import com.stampcrush.backend.application.manager.coupon.CustomerCouponStatistics;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class Coupons {

    private final List<Coupon> coupons;

    public CustomerCouponStatistics calculateStatistics() {
        int stampCount = 0;
        int rewardCount = 0;
        int maxStampCount = 0;

        for (Coupon coupon : coupons) {
            stampCount = calculateCurrentStampWhenUsingCoupon(stampCount, coupon);
            rewardCount += addRewardCouponCount(coupon);
            maxStampCount = coupon.calculateMaxStampCountWhenAccumulating();
        }
        return new CustomerCouponStatistics(stampCount, rewardCount, maxStampCount);
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
