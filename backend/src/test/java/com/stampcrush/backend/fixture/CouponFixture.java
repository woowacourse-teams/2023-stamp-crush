package com.stampcrush.backend.fixture;

import com.stampcrush.backend.entity.coupon.Coupon;
import com.stampcrush.backend.entity.coupon.CouponDesign;
import com.stampcrush.backend.entity.coupon.CouponPolicy;
import com.stampcrush.backend.entity.coupon.CouponStampCoordinate;

import java.time.LocalDate;
import java.util.List;

public final class CouponFixture {

    public static final Coupon GITCHAN_CAFE_COUPON = new Coupon(
            LocalDate.EPOCH,
            CustomerFixture.REGISTER_CUSTOMER_GITCHAN_SAVED,
            CafeFixture.GITCHAN_CAFE,
            new CouponDesign("front", "back", "stamp"),
            new CouponPolicy(10, "아메리카노", 8)
    );

    public static final List<CouponStampCoordinate> GITCHAN_CAFE_COUPON_STAMP_COORDINATE = List.of(
            new CouponStampCoordinate(1, 1, 1, new CouponDesign("front", "back", "stamp"))
    );

    private CouponFixture() {
    }
}
