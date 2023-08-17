package com.stampcrush.backend.fixture;

import com.stampcrush.backend.entity.coupon.Coupon;
import com.stampcrush.backend.entity.coupon.CouponStampCoordinate;

import java.time.LocalDate;
import java.util.List;

public final class CouponFixture {

    public static final Coupon GITCHAN_CAFE_COUPON = new Coupon(
            LocalDate.EPOCH,
            CustomerFixture.REGISTER_CUSTOMER_GITCHAN_SAVED,
            CafeFixture.GITCHAN_CAFE,
            CouponDesignFixture.COUPON_DESIGN_1,
            CouponPolicyFixture.COUPON_POLICY_1
    );

    public static final List<CouponStampCoordinate> GITCHAN_CAFE_COUPON_STAMP_COORDINATE = List.of(
            new CouponStampCoordinate(1, 1, 1, CouponDesignFixture.COUPON_DESIGN_1)
    );

    private CouponFixture() {
    }
}
