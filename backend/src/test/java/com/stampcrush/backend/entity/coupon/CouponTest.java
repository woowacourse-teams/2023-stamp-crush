package com.stampcrush.backend.entity.coupon;

import com.stampcrush.backend.entity.cafe.Cafe;
import com.stampcrush.backend.entity.user.Owner;
import com.stampcrush.backend.entity.user.TemporaryCustomer;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@SuppressWarnings("NonAsciiCharacters")
class CouponTest {

    @Test
    void 쿠폰이_현재_USING_상태인지_확인한다() {
        Coupon coupon = new Coupon(LocalDate.EPOCH, new TemporaryCustomer("이름", "번호"), new Cafe(
                "하디까페",
                LocalTime.of(12, 30),
                LocalTime.of(18, 30),
                "0211111111",
                "http://www.cafeImage.com",
                "잠실동12길",
                "14층",
                "11111111",
                new Owner("이름", "아이디", "비번", "번호")), new CouponDesign(), new CouponPolicy());
        assertAll(
                () -> assertThat(coupon.isUsing()).isTrue(),
                () -> assertThat(coupon.isRewarded()).isFalse()
        );
    }

    @Test
    void 쿠폰이_현재_REWARD_상태인지_확인한다() {
        Coupon coupon = new Coupon(LocalDate.EPOCH, new TemporaryCustomer("이름", "번호"), new Cafe(
                "하디까페",
                LocalTime.of(12, 30),
                LocalTime.of(18, 30),
                "0211111111",
                "http://www.cafeImage.com",
                "잠실동12길",
                "14층",
                "11111111",
                new Owner("이름", "아이디", "비번", "번호")), new CouponDesign(), new CouponPolicy());
        coupon.reward();
        assertAll(
                () -> assertThat(coupon.isUsing()).isFalse(),
                () -> assertThat(coupon.isRewarded()).isTrue()
        );
    }

    @Test
    void 스탬프를_적립한다() {
        // given
        Coupon coupon = new Coupon(LocalDate.EPOCH, new TemporaryCustomer("이름", "번호"), new Cafe(
                "하디까페",
                LocalTime.of(12, 30),
                LocalTime.of(18, 30),
                "0211111111",
                "http://www.cafeImage.com",
                "잠실동12길",
                "14층",
                "11111111",
                new Owner("이름", "아이디", "비번", "번호")), new CouponDesign(), new CouponPolicy(2, "짱", 10)
        );

        // when
        coupon.accumulate();

        // then
        assertThat(coupon.getStatus()).isSameAs(CouponStatus.USING);
        assertThat(coupon.getStampCount()).isEqualTo(1);
    }

    @Test
    void 스탬프를_적립하고_최대_스탬프_개수와_같아지면_쿠폰_상태가_REWARDED가_된다() {
        // given
        Coupon coupon = new Coupon(LocalDate.EPOCH, new TemporaryCustomer("이름", "번호"), new Cafe(
                "하디까페",
                LocalTime.of(12, 30),
                LocalTime.of(18, 30),
                "0211111111",
                "http://www.cafeImage.com",
                "잠실동12길",
                "14층",
                "11111111",
                new Owner("이름", "아이디", "비번", "번호")), new CouponDesign(), new CouponPolicy(2, "짱", 10)
        );

        // when
        coupon.accumulate();
        coupon.accumulate();

        // then
        assertThat(coupon.getStatus()).isSameAs(CouponStatus.REWARDED);
        assertThat(coupon.getStampCount()).isEqualTo(2);
    }
}
