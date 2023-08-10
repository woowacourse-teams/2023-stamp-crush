package com.stampcrush.backend.fixture;

import com.stampcrush.backend.entity.cafe.Cafe;
import com.stampcrush.backend.entity.cafe.CafePolicy;
import com.stampcrush.backend.entity.coupon.CouponPolicy;

public final class CouponPolicyFixture {

    public static final CouponPolicy COUPON_POLICY_1 = new CouponPolicy(10, "아메리카노", 8);
    public static final CouponPolicy COUPON_POLICY_2 = new CouponPolicy(10, "아메리카노", 8);
    public static final CouponPolicy COUPON_POLICY_3 = new CouponPolicy(10, "아메리카노", 8);
    public static final CouponPolicy COUPON_POLICY_4 = new CouponPolicy(10, "아메리카노", 8);
    public static final CouponPolicy COUPON_POLICY_5 = new CouponPolicy(10, "아메리카노", 8);
    public static final CouponPolicy COUPON_POLICY_6 = new CouponPolicy(10, "아메리카노", 8);
    public static final CouponPolicy COUPON_POLICY_7 = new CouponPolicy(20, "아메리카노", 8);

    public static CafePolicy cafePolicyOfSavedCafe(Cafe savedCafe) {
        return new CafePolicy(
                2,
                "아메리카노",
                12,
                false,
                savedCafe
        );
    }

    private CouponPolicyFixture() {
    }
}
