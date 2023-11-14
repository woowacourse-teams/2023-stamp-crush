package com.stampcrush.backend.fixture;

import com.stampcrush.backend.entity.cafe.CafeCouponDesign;
import com.stampcrush.backend.entity.cafe.CafePolicy;
import com.stampcrush.backend.entity.cafe.CafeStampCoordinate;
import com.stampcrush.backend.entity.coupon.Coupon;
import com.stampcrush.backend.entity.coupon.CouponDesign;
import com.stampcrush.backend.entity.coupon.CouponStampCoordinate;

import java.time.LocalDate;
import java.util.List;

public final class CouponFixture {

    public static final Coupon GITCHAN_CAFE_COUPON = new Coupon(
            LocalDate.EPOCH,
            CustomerFixture.REGISTER_CUSTOMER_GITCHAN_SAVED,
            CafeFixture.GITCHAN_CAFE,
            new CafeCouponDesign("front", "back", "stamp", null),
            new CafePolicy(10, "아메리카노", 8, null)
    );

    public static final List<CafeStampCoordinate> GITCHAN_CAFE_COUPON_STAMP_COORDINATE = List.of(
            new CafeStampCoordinate(1, 1, 1, new CafeCouponDesign("front", "back", "stamp", null))
    );

    private CouponFixture() {
    }
}
