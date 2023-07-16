package com.stampcrush.backend.entity.coupon;

import com.stampcrush.backend.entity.cafe.Cafe;
import com.stampcrush.backend.entity.user.TemporaryCustomer;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@SuppressWarnings("NonAsciiCharacters")
class CouponTest {

    @Test
    void 쿠폰이_현재_USING_상태인지_확인한다() {
        Coupon coupon = new Coupon(LocalDate.EPOCH, new TemporaryCustomer(), new Cafe(), new CouponDesign(), new CouponPolicy());
        assertAll(
                () -> assertThat(coupon.isUsing()).isTrue(),
                () -> assertThat(coupon.isRewarded()).isFalse()
        );
    }

    @Test
    void 쿠폰이_현재_REWARD_상태인지_확인한다() {
        Coupon coupon = new Coupon(LocalDate.EPOCH, new TemporaryCustomer(), new Cafe(), new CouponDesign(), new CouponPolicy());
        coupon.reward();
        assertAll(
                () -> assertThat(coupon.isUsing()).isFalse(),
                () -> assertThat(coupon.isRewarded()).isTrue()
        );
    }
}
