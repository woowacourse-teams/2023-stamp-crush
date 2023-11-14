package com.stampcrush.backend.fixture;

import com.stampcrush.backend.entity.cafe.Cafe;
import com.stampcrush.backend.entity.cafe.CafePolicy;
import com.stampcrush.backend.entity.coupon.CouponPolicy;

public final class CouponPolicyFixture {

    public static final CouponPolicy COUPON_POLICY_1 = new CouponPolicy(10, "아메리카노", 8);
    public static final CouponPolicy COUPON_POLICY_2 = new CouponPolicy(10, "아메리카노", 8);
    public static final CouponPolicy COUPON_POLICY_3 = new CouponPolicy(10, "아메리카노", 8);

    public static CafePolicy cafePolicyOfSavedCafe(Cafe savedCafe) {
        return new CafePolicy(
                2,
                "아메리카노",
                12,
                savedCafe
        );
    }

    private CouponPolicyFixture() {
    }
}
