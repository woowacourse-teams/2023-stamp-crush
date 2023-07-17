package com.stampcrush.backend.application.coupon;

import com.stampcrush.backend.application.coupon.dto.CafeCustomerInfoResultDto;
import com.stampcrush.backend.application.coupon.dto.CafeCustomersResultDto;
import com.stampcrush.backend.application.coupon.dto.CustomerUsingCouponResultDto;
import com.stampcrush.backend.entity.cafe.Cafe;
import com.stampcrush.backend.entity.coupon.Coupon;
import com.stampcrush.backend.entity.coupon.CouponDesign;
import com.stampcrush.backend.entity.coupon.CouponPolicy;
import com.stampcrush.backend.entity.coupon.Stamp;
import com.stampcrush.backend.entity.user.Owner;
import com.stampcrush.backend.entity.user.RegisterCustomer;
import com.stampcrush.backend.entity.user.TemporaryCustomer;
import com.stampcrush.backend.repository.cafe.CafeRepository;
import com.stampcrush.backend.repository.coupon.CouponDesignRepository;
import com.stampcrush.backend.repository.coupon.CouponPolicyRepository;
import com.stampcrush.backend.repository.coupon.CouponRepository;
import com.stampcrush.backend.repository.user.OwnerRepository;
import com.stampcrush.backend.repository.user.RegisterCustomerRepository;
import com.stampcrush.backend.repository.user.TemporaryCustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SuppressWarnings("NonAsciiCharacters")
@Transactional
@SpringBootTest
class CouponServiceTest {

    @Autowired
    private CouponService couponService;

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

    // coupon1, REWARD상태, cafe1 -> stamp0개, 임시유저
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
        tmpCustomer1 = temporaryCustomerRepository.save(new TemporaryCustomer("깃짱 닉네임", "깃짱 번호"));
        tmpCustomer2 = temporaryCustomerRepository.save(new TemporaryCustomer("깃짱 닉네임", "깃짱 번호"));
        tmpCustomer3 = temporaryCustomerRepository.save(new TemporaryCustomer("깃짱 닉네임", "깃짱 번호"));

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

        couponDesign1 = couponDesignRepository.save(new CouponDesign());
        couponDesign2 = couponDesignRepository.save(new CouponDesign());
        couponDesign3 = couponDesignRepository.save(new CouponDesign());
        couponDesign4 = couponDesignRepository.save(new CouponDesign());
        couponDesign5 = couponDesignRepository.save(new CouponDesign());

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
    void 조회하려는_카페가_존재하지_않으면_예외발생() {
        assertThatThrownBy(() -> couponService.findCouponsByCafe(100L))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void 카페_별_고객들의_정보를_조회한다() {
        // when
        CafeCustomersResultDto customers = couponService.findCouponsByCafe(cafe1.getId());

        CafeCustomerInfoResultDto customer1Info = new CafeCustomerInfoResultDto(
                tmpCustomer1.getId(),
                tmpCustomer1.getNickname(),
                0,
                1,
                2,
                coupon1.getCreatedAt(),
                false
        );

        CafeCustomerInfoResultDto customer2Info = new CafeCustomerInfoResultDto(
                registerCustomer1.getId(),
                registerCustomer1.getNickname(),
                1,
                0,
                1,
                coupon2.getCreatedAt(),
                true
        );

        // then
        assertThat(customers.getCustomers()).containsExactlyInAnyOrder(customer1Info, customer2Info);
    }

    @Test
    void 존재X_현재_스탬프를_모으고_있는_쿠폰_정보를_조회한다() {
        // when
        List<CustomerUsingCouponResultDto> result = couponService.findAccumulatingCoupon(cafe1.getId(), tmpCustomer1.getId());

        // then
        assertThat(result).isEmpty();
    }

    @Test
    void 존재O_현재_스탬프를_모으고_있는_쿠폰_정보를_조회한다() {
        // when
        List<CustomerUsingCouponResultDto> result = couponService.findAccumulatingCoupon(cafe1.getId(), registerCustomer1.getId());
        CustomerUsingCouponResultDto customerUsingCoupon = new CustomerUsingCouponResultDto(
                coupon2.getId(),
                registerCustomer1.getId(),
                registerCustomer1.getNickname(),
                coupon2.getStampCount(),
                coupon2.calculateExpireDate(),
                false);

        //then
        assertThat(result).containsExactly(customerUsingCoupon);
    }

    @Test
    void 첫_방문일자를_계산한다() {
        // given
        CouponDesign couponDesign6 = couponDesignRepository.save(new CouponDesign());
        CouponPolicy couponPolicy6 = couponPolicyRepository.save(new CouponPolicy(10, "아메리카노", 10));

        Coupon coupon6 = new Coupon(LocalDate.now(), tmpCustomer1, cafe1, couponDesign6, couponPolicy6);
        Stamp stamp6 = new Stamp();

        stamp6.registerCoupon(coupon6);
        couponRepository.save(coupon6);

        // when
        CafeCustomersResultDto customersResponseDto = couponService.findCouponsByCafe(cafe1.getId());
        // 첫 방문일자는 먼저 저장된 coupon1의 createdAt이어야 한다.
        CafeCustomerInfoResultDto expected = new CafeCustomerInfoResultDto(
                tmpCustomer1.getId(),
                tmpCustomer1.getNickname(),
                coupon6.getStampCount(),
                1,
                3,
                coupon1.getCreatedAt(),
                false
        );

        //then
        assertThat(customersResponseDto.getCustomers()).containsAnyOf(expected);
    }
}
