package com.stampcrush.backend.fixture;

import com.stampcrush.backend.entity.coupon.Coupon;

import java.time.LocalDate;

public class CouponFixture {

    public static final Coupon GITCHAN_COUPON = new Coupon(
            LocalDate.EPOCH,
            CustomerFixture.REGISTER_CUSTOMER_GITCHAN,
            CafeFixture.GITCHAN_CAFE,
            CouponDesignFixture.COUPON_DESIGN_1,
            CouponPolicyFixture.COUPON_POLICY_1
    );
}
