package com.stampcrush.backend.repository.coupon;

import com.stampcrush.backend.entity.cafe.Cafe;
import com.stampcrush.backend.entity.coupon.*;
import com.stampcrush.backend.entity.user.Owner;
import com.stampcrush.backend.entity.user.RegisterCustomer;
import com.stampcrush.backend.entity.user.TemporaryCustomer;
import com.stampcrush.backend.repository.cafe.CafeRepository;
import com.stampcrush.backend.repository.user.OwnerRepository;
import com.stampcrush.backend.repository.user.RegisterCustomerRepository;
import com.stampcrush.backend.repository.user.TemporaryCustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@SuppressWarnings("NonAsciiCharacters")
@DataJpaTest
class CouponRepositoryTest {

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private TemporaryCustomerRepository temporaryCustomerRepository;

    @Autowired
    private RegisterCustomerRepository registerCustomerRepository;

    @Autowired
    private CafeRepository cafeRepository;

    @Autowired
    private CouponDesignRepository couponDesignRepository;

    @Autowired
    private CouponPolicyRepository couponPolicyRepository;

    @Autowired
    private OwnerRepository ownerRepository;

    private TemporaryCustomer tmpCustomer1;
    private TemporaryCustomer tmpCustomer2;
    private TemporaryCustomer tmpCustomer3;

    private RegisterCustomer registerCustomer1;
    private RegisterCustomer registerCustomer2;

    private Cafe cafe1;
    private Cafe cafe2;

    private CouponDesign couponDesign1;
    private CouponDesign couponDesign2;
    private CouponDesign couponDesign3;
    private CouponDesign couponDesign4;
    private CouponDesign couponDesign5;

    private CouponPolicy couponPolicy1;
    private CouponPolicy couponPolicy2;
    private CouponPolicy couponPolicy3;
    private CouponPolicy couponPolicy4;
    private CouponPolicy couponPolicy5;

    // coupon1, REWARD상태, cafe1 -> stamp2개
    // coupon2, USING상태, cafe1 -> stamp1개
    // coupon3, USING상태, cafe2 -> stamp1개
    // coupon4, USING상태, cafe2 -> stamp0개
    // coupon5, USING상태, cafe2 -> stamp0개
    private Coupon coupon1;
    private Coupon coupon2;
    private Coupon coupon3;
    private Coupon coupon4;
    private Coupon coupon5;

    @BeforeEach
    void setUp() {
        tmpCustomer1 = temporaryCustomerRepository.save(TemporaryCustomer.from("깃짱 번호"));
        tmpCustomer2 = temporaryCustomerRepository.save(TemporaryCustomer.from("깃짱 번호"));
        tmpCustomer3 = temporaryCustomerRepository.save(TemporaryCustomer.from("깃짱 번호"));

        registerCustomer1 = registerCustomerRepository.save(new RegisterCustomer("깃짱 닉네임", "깃짱 번호", "깃짱 아이디", "깃짱 비번"));
        registerCustomer2 = registerCustomerRepository.save(new RegisterCustomer("깃짱 닉네임", "깃짱 번호", "깃짱 아이디", "깃짱 비번"));

        cafe1 = cafeRepository.save(new Cafe(
                "하디까페",
                LocalTime.of(12, 30),
                LocalTime.of(18, 30),
                "0211111111",
                "http://www.cafeImage.com",
                "잠실동12길",
                "14층",
                "11111111",
                ownerRepository.save(new Owner("이름", "아이디", "비번", "번호"))));
        cafe2 = cafeRepository.save(new Cafe(
                "하디까페",
                LocalTime.of(12, 30),
                LocalTime.of(18, 30),
                "0211111111",
                "http://www.cafeImage.com",
                "잠실동12길",
                "14층",
                "11111111",
                ownerRepository.save(new Owner("이름", "아이디", "비번", "번호"))));

        couponDesign1 = couponDesignRepository.save(new CouponDesign("front", "back", "stamp"));
        couponDesign2 = couponDesignRepository.save(new CouponDesign("front", "back", "stamp"));
        couponDesign3 = couponDesignRepository.save(new CouponDesign("front", "back", "stamp"));
        couponDesign4 = couponDesignRepository.save(new CouponDesign("front", "back", "stamp"));
        couponDesign5 = couponDesignRepository.save(new CouponDesign("front", "back", "stamp"));

        couponPolicy1 = couponPolicyRepository.save(new CouponPolicy(10, "아메리카노", 8));
        couponPolicy2 = couponPolicyRepository.save(new CouponPolicy(10, "아메리카노", 8));
        couponPolicy3 = couponPolicyRepository.save(new CouponPolicy(10, "아메리카노", 8));
        couponPolicy4 = couponPolicyRepository.save(new CouponPolicy(10, "아메리카노", 8));
        couponPolicy5 = couponPolicyRepository.save(new CouponPolicy(10, "아메리카노", 8));

        coupon1 = new Coupon(LocalDate.EPOCH, tmpCustomer1, cafe1, couponDesign1, couponPolicy1);
        Stamp stamp1 = new Stamp();
        Stamp stamp2 = new Stamp();
        stamp1.registerCoupon(coupon1);
        stamp2.registerCoupon(coupon1);
        Coupon save = couponRepository.save(coupon1);
        save.reward();

        coupon2 = new Coupon(LocalDate.EPOCH, registerCustomer1, cafe1, couponDesign2, couponPolicy2);
        Stamp stamp3 = new Stamp();
        stamp3.registerCoupon(coupon2);
        couponRepository.save(coupon2);

        coupon3 = new Coupon(LocalDate.EPOCH, tmpCustomer2, cafe2, couponDesign3, couponPolicy3);
        Stamp stamp4 = new Stamp();
        stamp4.registerCoupon(coupon3);
        couponRepository.save(coupon3);

        coupon4 = new Coupon(LocalDate.EPOCH, tmpCustomer3, cafe2, couponDesign4, couponPolicy4);
        couponRepository.save(coupon4);

        coupon5 = new Coupon(LocalDate.EPOCH, registerCustomer2, cafe2, couponDesign5, couponPolicy5);
        couponRepository.save(coupon5);
    }

    @Test
    void 카페에_맞는_쿠폰_정보를_조회한다() {
        // when
        List<Coupon> couponsCafe1 = couponRepository.findByCafe(cafe1);
        List<Coupon> couponsCafe2 = couponRepository.findByCafe(cafe2);

        //then
        assertAll(
                () -> assertThat(couponsCafe1.size()).isEqualTo(2),
                () -> assertThat(couponsCafe1).containsExactlyInAnyOrder(coupon1, coupon2),
                () -> assertThat(couponsCafe1.get(0).getStamps().size()).isEqualTo(2),
                () -> assertThat(couponsCafe1).doesNotContain(coupon3, coupon4, coupon5),
                () -> assertThat(couponsCafe2.size()).isEqualTo(3),
                () -> assertThat(couponsCafe2).containsExactlyInAnyOrder(coupon3, coupon4, coupon5),
                () -> assertThat(couponsCafe2).doesNotContain(coupon1, coupon2)
        );
    }

    @Test
    void 쿠폰X_카페에_스탬프를_적립하고_있는_고객의_쿠폰을_조회한다() {
        // then
        List<Coupon> customerCoupon = couponRepository.findByCafeAndCustomerAndStatus(cafe1, tmpCustomer1, CouponStatus.ACCUMULATING);

        assertThat(customerCoupon).isEmpty();
    }

    @Test
    void 쿠폰O_카페에_스탬프를_적립하고_있는_고객의_쿠폰을_조회한다() {
        // when
        List<Coupon> customerCoupon = couponRepository.findByCafeAndCustomerAndStatus(cafe2, tmpCustomer3, CouponStatus.ACCUMULATING);
        // then
        assertThat(customerCoupon).containsExactly(coupon4);
    }

    @Test
    void 쿠폰의_유효기간_만료일을_계산한다() {
        // given, when
        LocalDateTime expiredDate = coupon1.calculateExpireDate();
        LocalDateTime expected = coupon1.getCreatedAt().plusMonths(couponPolicy1.getExpiredPeriod());

        // then
        assertThat(expiredDate).isEqualTo(expected);
    }

    // 첫 방문일자 구하기 위한 기능
    @Test
    void 쿠폰들의_createdAt을_비교한다() {
        // given, when
        LocalDateTime visitTime = coupon1.compareCreatedAtAndReturnEarlier(coupon5.getCreatedAt());

        // then
        assertThat(visitTime).isEqualTo(coupon1.getCreatedAt());
    }
}
