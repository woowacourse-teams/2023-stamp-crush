package com.stampcrush.backend.entity.coupon;

import com.stampcrush.backend.common.KorNamingConverter;
import com.stampcrush.backend.entity.cafe.Cafe;
import com.stampcrush.backend.entity.cafe.CafeCouponDesign;
import com.stampcrush.backend.entity.cafe.CafePolicy;
import com.stampcrush.backend.entity.user.Customer;
import com.stampcrush.backend.entity.user.Owner;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@KorNamingConverter
class CouponTest {

    private final Customer temporaryCustomer = Customer.temporaryCustomerBuilder()
            .phoneNumber("01012345678")
            .build();

    @Test
    void 쿠폰이_현재_USING_상태인지_확인한다() {
        // given, when
        Coupon coupon = new Coupon(LocalDate.EPOCH, temporaryCustomer, new Cafe(
                "하디까페",
                LocalTime.of(12, 30),
                LocalTime.of(18, 30),
                "0211111111",
                "http://www.cafeImage.com",
                "안녕하세요",
                "잠실동12길",
                "14층",
                "11111111",
                new Owner("이름", "아이디", "비번", "01012345678")),
                new CafeCouponDesign("#", "#", "#", null),
                new CafePolicy(5, "#", 1, null)
        );

        // then
        assertAll(
                () -> assertThat(coupon.isAccumulating()).isTrue(),
                () -> assertThat(coupon.isRewarded()).isFalse()
        );
    }

    @Test
    void 쿠폰이_현재_REWARD_상태인지_확인한다() {
        // given
        Coupon coupon = new Coupon(LocalDate.EPOCH, temporaryCustomer, new Cafe(
                "하디까페",
                LocalTime.of(12, 30),
                LocalTime.of(18, 30),
                "0211111111",
                "http://www.cafeImage.com",
                "안녕하세요",
                "잠실동12길",
                "14층",
                "11111111",
                new Owner("이름", "아이디", "비번", "01012345678")),
                new CafeCouponDesign("#", "#", "#", null),
                new CafePolicy(5, "#", 1, null)
        );

        // when
        coupon.reward();

        // then
        assertAll(
                () -> assertThat(coupon.isAccumulating()).isFalse(),
                () -> assertThat(coupon.isRewarded()).isTrue()
        );
    }

    @Test
    void 스탬프를_적립한다() {
        // given
        Coupon coupon = new Coupon(LocalDate.EPOCH, temporaryCustomer, new Cafe(
                "하디까페",
                LocalTime.of(12, 30),
                LocalTime.of(18, 30),
                "0211111111",
                "http://www.cafeImage.com",
                "안녕하세요",
                "잠실동12길",
                "14층",
                "11111111",
                new Owner("이름", "아이디", "비번", "01012345678")),
                new CafeCouponDesign("#", "#", "#", null),
                new CafePolicy(5, "#", 1, null)
        );

        // when
        coupon.accumulate(1);

        // then
        assertThat(coupon.getStatus()).isSameAs(CouponStatus.ACCUMULATING);
        assertThat(coupon.getStampCount()).isEqualTo(1);
    }

    @Test
    void 스탬프를_적립하고_최대_스탬프_개수와_같아지면_쿠폰_상태가_REWARDED가_된다() {
        // given
        Coupon coupon = new Coupon(LocalDate.EPOCH, temporaryCustomer, new Cafe(
                "하디까페",
                LocalTime.of(12, 30),
                LocalTime.of(18, 30),
                "0211111111",
                "http://www.cafeImage.com",
                "안녕하세요",
                "잠실동12길",
                "14층",
                "11111111",
                new Owner("이름", "아이디", "비번", "01012345678")),
                new CafeCouponDesign("#", "#", "#", null),
                new CafePolicy(5, "#", 1, null)
        );

        // when
        coupon.accumulate(2);

        // then
        assertThat(coupon.getStatus()).isSameAs(CouponStatus.REWARDED);
        assertThat(coupon.getStampCount()).isEqualTo(2);
    }

    @Test
    void 스탬프를_적립하고_최대_스탬프_개수와_같아지면_쿠폰_상태가_REWARDED가_된다1() {
        // given
        Coupon coupon = new Coupon(LocalDate.EPOCH, temporaryCustomer, new Cafe(
                "하디까페",
                LocalTime.of(12, 30),
                LocalTime.of(18, 30),
                "0211111111",
                "http://www.cafeImage.com",
                "안녕하세요",
                "잠실동12길",
                "14층",
                "11111111",
                new Owner("이름", "아이디", "비번", "01012345678")),
                new CafeCouponDesign("#", "#", "#", null),
                new CafePolicy(5, "#", 1, null)
        );

        // when
        coupon.accumulate(2);

        // then
        assertThat(coupon.getStatus()).isSameAs(CouponStatus.REWARDED);
        assertThat(coupon.getStampCount()).isEqualTo(2);
    }

    @Test
    void 보상까지_남은_스탬프_개수를_계산한다() {
        // given
        Coupon coupon = new Coupon(LocalDate.EPOCH, temporaryCustomer, new Cafe(
                "하디까페",
                LocalTime.of(12, 30),
                LocalTime.of(18, 30),
                "0211111111",
                "http://www.cafeImage.com",
                "안녕하세요",
                "잠실동12길",
                "14층",
                "11111111",
                new Owner("이름", "아이디", "비번", "01012345678")),
                new CafeCouponDesign("#", "#", "#", null),
                new CafePolicy(5, "#", 1, null)
        );

        // when
        coupon.accumulate(3);
        int restStampCount = coupon.calculateRestStampCountForReward();

        // then
        assertThat(restStampCount).isEqualTo(7);
    }

    @Test
    void 스탬프_적립_시_찍힌_스탬프_합이_보상_개수보다_적으면_true다() {
        // given
        Coupon coupon = new Coupon(LocalDate.EPOCH, temporaryCustomer, new Cafe(
                "하디까페",
                LocalTime.of(12, 30),
                LocalTime.of(18, 30),
                "0211111111",
                "http://www.cafeImage.com",
                "안녕하세요",
                "잠실동12길",
                "14층",
                "11111111",
                new Owner("이름", "아이디", "비번", "01012345678")),
                new CafeCouponDesign("#", "#", "#", null),
                new CafePolicy(5, "#", 1, null)
        );

        // when
        coupon.accumulate(7);

        // then
        assertThat(coupon.isLessThanMaxStampAfterAccumulateStamp(2)).isTrue();
    }

    @Test
    void 스탬프_적립_시_찍힌_스탬프_합이_보상_개수와_같으면_true다() {
        // given
        Coupon coupon = new Coupon(LocalDate.EPOCH, temporaryCustomer, new Cafe(
                "하디까페",
                LocalTime.of(12, 30),
                LocalTime.of(18, 30),
                "0211111111",
                "http://www.cafeImage.com",
                "안녕하세요",
                "잠실동12길",
                "14층",
                "11111111",
                new Owner("이름", "아이디", "비번", "01012345678")),
                new CafeCouponDesign("#", "#", "#", null),
                new CafePolicy(5, "#", 1, null)
        );

        // when
        coupon.accumulate(7);

        // then
        assertThat(coupon.isSameMaxStampAfterAccumulateStamp(3)).isTrue();
    }
}
