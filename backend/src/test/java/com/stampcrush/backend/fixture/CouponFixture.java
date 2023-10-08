package com.stampcrush.backend.fixture;

import com.stampcrush.backend.entity.cafe.CafeStampCoordinate;
import com.stampcrush.backend.entity.coupon.Coupon;

import java.time.LocalDate;
import java.util.List;

public final class CouponFixture {

    public static final Coupon GITCHAN_CAFE_COUPON = new Coupon(
            LocalDate.EPOCH,
            CustomerFixture.REGISTER_CUSTOMER_GITCHAN_SAVED,
            CafeFixture.GITCHAN_CAFE,
            CafeCouponDesignFixture.COUPON_DESIGN_1,
            CafePolicyFixture.COUPON_POLICY_1
    );

    public static final List<CafeStampCoordinate> GITCHAN_CAFE_COUPON_STAMP_COORDINATE = List.of(
            new CafeStampCoordinate(1, 1, 1, CafeCouponDesignFixture.COUPON_DESIGN_1)
    );

    private CouponFixture() {
    }
}
