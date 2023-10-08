package com.stampcrush.backend.fixture;

import com.stampcrush.backend.entity.cafe.Cafe;
import com.stampcrush.backend.entity.cafe.CafeCouponDesign;

public final class CafeCouponDesignFixture {

    public static final CafeCouponDesign COUPON_DESIGN_1 = new CafeCouponDesign("front", "back", "stamp", CafeFixture.GITCHAN_CAFE);
    public static final CafeCouponDesign COUPON_DESIGN_2 = new CafeCouponDesign("front", "back", "stamp", CafeFixture.GITCHAN_CAFE);
    public static final CafeCouponDesign COUPON_DESIGN_3 = new CafeCouponDesign("front", "back", "stamp", CafeFixture.GITCHAN_CAFE);
    public static final CafeCouponDesign COUPON_DESIGN_4 = new CafeCouponDesign("front", "back", "stamp", CafeFixture.GITCHAN_CAFE);
    public static final CafeCouponDesign COUPON_DESIGN_5 = new CafeCouponDesign("front", "back", "stamp", CafeFixture.GITCHAN_CAFE);
    public static final CafeCouponDesign COUPON_DESIGN_6 = new CafeCouponDesign("front", "back", "stamp", CafeFixture.GITCHAN_CAFE);
    public static final CafeCouponDesign COUPON_DESIGN_7 = new CafeCouponDesign("front", "back", "stamp", CafeFixture.GITCHAN_CAFE);

    public static CafeCouponDesign cafeCouponDesignOfSavedCafe(Cafe savedCafe) {
        return new CafeCouponDesign(
                "#",
                "#",
                "#",
                savedCafe
        );
    }

    private CafeCouponDesignFixture() {
    }
}
