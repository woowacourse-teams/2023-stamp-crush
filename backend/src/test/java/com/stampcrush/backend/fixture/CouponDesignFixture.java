package com.stampcrush.backend.fixture;

import com.stampcrush.backend.entity.cafe.Cafe;
import com.stampcrush.backend.entity.cafe.CafeCouponDesign;
import com.stampcrush.backend.entity.coupon.CouponDesign;

public final class CouponDesignFixture {

    public static final CouponDesign COUPON_DESIGN_1 = new CouponDesign("front", "back", "stamp");
    public static final CouponDesign COUPON_DESIGN_2 = new CouponDesign("front", "back", "stamp");
    public static final CouponDesign COUPON_DESIGN_3 = new CouponDesign("front", "back", "stamp");
    public static final CouponDesign COUPON_DESIGN_4 = new CouponDesign("front", "back", "stamp");
    public static final CouponDesign COUPON_DESIGN_5 = new CouponDesign("front", "back", "stamp");
    public static final CouponDesign COUPON_DESIGN_6 = new CouponDesign("front", "back", "stamp");
    public static final CouponDesign COUPON_DESIGN_7 = new CouponDesign("front", "back", "stamp");

    public static CafeCouponDesign cafeCouponDesignOfSavedCafe(Cafe savedCafe) {
        return new CafeCouponDesign(
                "#",
                "#",
                "#",
                savedCafe
        );
    }

    private CouponDesignFixture() {
    }
}
