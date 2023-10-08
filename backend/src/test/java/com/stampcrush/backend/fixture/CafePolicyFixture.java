package com.stampcrush.backend.fixture;

import com.stampcrush.backend.entity.cafe.Cafe;
import com.stampcrush.backend.entity.cafe.CafePolicy;

public final class CafePolicyFixture {

    public static final CafePolicy COUPON_POLICY_1 = new CafePolicy(10, "아메리카노", 8, CafeFixture.GITCHAN_CAFE);
    public static final CafePolicy COUPON_POLICY_2 = new CafePolicy(10, "아메리카노", 8, CafeFixture.GITCHAN_CAFE);
    public static final CafePolicy COUPON_POLICY_3 = new CafePolicy(10, "아메리카노", 8, CafeFixture.GITCHAN_CAFE);

    public static CafePolicy cafePolicyOfSavedCafe(Cafe savedCafe) {
        return new CafePolicy(
                2,
                "아메리카노",
                12,
                savedCafe
        );
    }

    private CafePolicyFixture() {
    }
}
