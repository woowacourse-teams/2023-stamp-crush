package com.stampcrush.backend.application.coupon;

import com.stampcrush.backend.application.coupon.dto.CafeCustomerFindResultDto;
import com.stampcrush.backend.application.coupon.dto.CafeCustomersFindResultDto;
import com.stampcrush.backend.application.coupon.dto.CustomerAccumulatingCouponFindResultDto;
import com.stampcrush.backend.application.coupon.dto.StampCreateDto;
import com.stampcrush.backend.entity.cafe.Cafe;
import com.stampcrush.backend.entity.cafe.CafeCouponDesign;
import com.stampcrush.backend.entity.cafe.CafePolicy;
import com.stampcrush.backend.entity.coupon.*;
import com.stampcrush.backend.entity.user.Owner;
import com.stampcrush.backend.entity.user.RegisterCustomer;
import com.stampcrush.backend.entity.user.TemporaryCustomer;
import com.stampcrush.backend.repository.cafe.CafeCouponDesignRepository;
import com.stampcrush.backend.repository.cafe.CafePolicyRepository;
import com.stampcrush.backend.repository.cafe.CafeRepository;
import com.stampcrush.backend.repository.coupon.CouponDesignRepository;
import com.stampcrush.backend.repository.coupon.CouponPolicyRepository;
import com.stampcrush.backend.repository.coupon.CouponRepository;
import com.stampcrush.backend.repository.reward.RewardRepository;
import com.stampcrush.backend.repository.user.OwnerRepository;
import com.stampcrush.backend.repository.user.RegisterCustomerRepository;
import com.stampcrush.backend.repository.user.TemporaryCustomerRepository;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
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

    @Autowired
    private CafeCouponDesignRepository cafeCouponDesignRepository;

    @Autowired
    private CafePolicyRepository cafePolicyRepository;

    @Autowired
    private RewardRepository rewardRepository;

    private TemporaryCustomer tmpCustomer1;
    private TemporaryCustomer tmpCustomer2;
    private TemporaryCustomer tmpCustomer3;

    private RegisterCustomer registerCustomer1;
    private RegisterCustomer registerCustomer2;

    private Cafe cafe1;
    private Cafe cafe2;

    private CafeCouponDesign cafeCouponDesign1;
    private CafeCouponDesign cafeCouponDesign2;

    private CafePolicy cafePolicy1;
    private CafePolicy cafePolicy2;

    private CouponDesign couponDesign1;
    private CouponDesign couponDesign2;
    private CouponDesign couponDesign3;
    private CouponDesign couponDesign4;
    private CouponDesign couponDesign5;
    private CouponDesign couponDesign6;

    private CouponPolicy couponPolicy1;
    private CouponPolicy couponPolicy2;
    private CouponPolicy couponPolicy3;
    private CouponPolicy couponPolicy4;
    private CouponPolicy couponPolicy5;
    private CouponPolicy couponPolicy6;

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
    private Coupon coupon6;

    private Owner owner1;
    private Owner owner2;

    @BeforeEach
    void setUp() {
        tmpCustomer1 = temporaryCustomerRepository.save(new TemporaryCustomer("깃짱 닉네임", "깃짱 번호"));
        tmpCustomer2 = temporaryCustomerRepository.save(new TemporaryCustomer("깃짱 닉네임", "깃짱 번호"));
        tmpCustomer3 = temporaryCustomerRepository.save(new TemporaryCustomer("깃짱 닉네임", "깃짱 번호"));

        registerCustomer1 = registerCustomerRepository.save(new RegisterCustomer("깃짱 닉네임", "깃짱 번호", "깃짱 아이디", "깃짱 비번"));
        registerCustomer2 = registerCustomerRepository.save(new RegisterCustomer("깃짱 닉네임", "깃짱 번호", "깃짱 아이디", "깃짱 비번"));

        owner1 = ownerRepository.save(new Owner("이름", "아이디", "비번", "번호"));
        owner2 = ownerRepository.save(new Owner("이름", "아이디", "비번", "번호"));

        cafe1 = cafeRepository.save(new Cafe(
                "하디까페",
                LocalTime.of(12, 30),
                LocalTime.of(18, 30),
                "0211111111",
                "http://www.cafeImage.com",
                "잠실동12길",
                "14층",
                "11111111",
                owner1));
        cafe2 = cafeRepository.save(new Cafe(
                "하디까페",
                LocalTime.of(12, 30),
                LocalTime.of(18, 30),
                "0211111111",
                "http://www.cafeImage.com",
                "잠실동12길",
                "14층",
                "11111111",
                owner2));

        cafeCouponDesign1 = cafeCouponDesignRepository.save(
                new CafeCouponDesign(
                        "past_#",
                        "past_#",
                        "past_#",
                        true,
                        cafe1
                )
        );

        cafeCouponDesign2 = cafeCouponDesignRepository.save(
                new CafeCouponDesign(
                        "cur_#",
                        "cur_#",
                        "cur_#",
                        false,
                        cafe1
                )
        );

        cafePolicy1 = cafePolicyRepository.save(
                new CafePolicy(
                        2,
                        "아메리카노",
                        12,
                        true,
                        cafe1
                )
        );

        cafePolicy2 = cafePolicyRepository.save(
                new CafePolicy(
                        10,
                        "아메리카노",
                        12,
                        false,
                        cafe1
                )
        );

        couponDesign1 = couponDesignRepository.save(new CouponDesign("front", "back", "stamp"));
        couponDesign2 = couponDesignRepository.save(new CouponDesign("front", "back", "stamp"));
        couponDesign3 = couponDesignRepository.save(new CouponDesign("front", "back", "stamp"));
        couponDesign4 = couponDesignRepository.save(new CouponDesign("front", "back", "stamp"));
        couponDesign5 = couponDesignRepository.save(new CouponDesign("front", "back", "stamp"));
        couponDesign6 = couponDesignRepository.save(new CouponDesign("front", "back", "stamp"));

        couponPolicy1 = couponPolicyRepository.save(new CouponPolicy(10, "아메리카노", 8));
        couponPolicy2 = couponPolicyRepository.save(new CouponPolicy(10, "아메리카노", 8));
        couponPolicy3 = couponPolicyRepository.save(new CouponPolicy(10, "아메리카노", 8));
        couponPolicy4 = couponPolicyRepository.save(new CouponPolicy(10, "아메리카노", 8));
        couponPolicy5 = couponPolicyRepository.save(new CouponPolicy(10, "아메리카노", 8));
        couponPolicy6 = couponPolicyRepository.save(new CouponPolicy(10, "아메리카노", 8));

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

        coupon6 = new Coupon(LocalDate.EPOCH, registerCustomer2, cafe1, couponDesign6, couponPolicy6);
        couponRepository.save(coupon6);
    }

    @Test
    void 조회하려는_카페가_존재하지_않으면_예외발생() {
        assertThatThrownBy(() -> couponService.findCouponsByCafe(100L))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void 카페_별_고객들의_정보를_조회한다() {
        // when
        CafeCustomersFindResultDto customers = couponService.findCouponsByCafe(cafe1.getId());

        CafeCustomerFindResultDto customer1Info = new CafeCustomerFindResultDto(
                tmpCustomer1.getId(),
                tmpCustomer1.getNickname(),
                0,
                1,
                2,
                coupon1.getCreatedAt(),
                false
        );

        CafeCustomerFindResultDto customer2Info = new CafeCustomerFindResultDto(
                registerCustomer1.getId(),
                registerCustomer1.getNickname(),
                1,
                0,
                1,
                coupon2.getCreatedAt(),
                true
        );

        CafeCustomerFindResultDto customer3Info = new CafeCustomerFindResultDto(
                registerCustomer2.getId(),
                registerCustomer2.getNickname(),
                0,
                0,
                0,
                coupon6.getCreatedAt(),
                true
        );
        assertThat(customers.getCustomers()).containsExactlyInAnyOrder(customer1Info, customer2Info, customer3Info);
    }

    @Test
    void 존재X_현재_스탬프를_모으고_있는_쿠폰_정보를_조회한다() {
        // when
        List<CustomerAccumulatingCouponFindResultDto> result = couponService.findAccumulatingCoupon(cafe1.getId(), tmpCustomer1.getId());

        // then
        assertThat(result).isEmpty();
    }

    @Test
    void 존재O_현재_스탬프를_모으고_있는_쿠폰_정보를_조회한다() {
        // when
        List<CustomerAccumulatingCouponFindResultDto> result = couponService.findAccumulatingCoupon(cafe1.getId(), registerCustomer1.getId());
        CustomerAccumulatingCouponFindResultDto customerUsingCoupon = new CustomerAccumulatingCouponFindResultDto(
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
        CouponDesign couponDesign6 = couponDesignRepository.save(new CouponDesign("front", "Back", "Stamp"));
        CouponPolicy couponPolicy6 = couponPolicyRepository.save(new CouponPolicy(10, "아메리카노", 10));

        Coupon coupon6 = new Coupon(LocalDate.now(), tmpCustomer1, cafe1, couponDesign6, couponPolicy6);
        Stamp stamp6 = new Stamp();

        stamp6.registerCoupon(coupon6);
        couponRepository.save(coupon6);

        // when
        CafeCustomersFindResultDto customersResponseDto = couponService.findCouponsByCafe(cafe1.getId());
        // 첫 방문일자는 먼저 저장된 coupon1의 createdAt이어야 한다.
        CafeCustomerFindResultDto expected = new CafeCustomerFindResultDto(
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

    @Test
    void 쿠폰_정책_변경_이전에_발급받은_쿠폰인지_확인한다() {
        // given
        Cafe cafe3 = cafeRepository.save(new Cafe(
                "하디까페",
                LocalTime.of(12, 30),
                LocalTime.of(18, 30),
                "0211111111",
                "http://www.cafeImage.com",
                "잠실동12길",
                "14층",
                "11111111",
                ownerRepository.save(new Owner("이름", "아이디", "비번", "번호"))));

        CafePolicy oldCafePolicy = cafePolicyRepository.save(
                new CafePolicy(
                        2,
                        "마카롱",
                        12,
                        true,
                        cafe3
                )
        );

        // when (예전 정책일 때 쿠폰을 발급받는다)
        CouponDesign couponDesign7 = couponDesignRepository.save(new CouponDesign("front", "Back", "Stamp"));
        CouponPolicy couponPolicy7 = couponPolicyRepository.save(new CouponPolicy(oldCafePolicy.getMaxStampCount(), oldCafePolicy.getReward(), oldCafePolicy.getExpirePeriod()));

        couponRepository.save(new Coupon(LocalDate.now(), tmpCustomer1, cafe3, couponDesign7, couponPolicy7));

        // when 새로운 정책으로 변경하고 쿠폰을 저장한다
        cafePolicyRepository.save(
                new CafePolicy(
                        2,
                        "마카롱",
                        12,
                        false,
                        cafe3
                )
        );

        CouponDesign couponDesign8 = couponDesignRepository.save(new CouponDesign("front", "Back", "Stamp"));
        CouponPolicy couponPolicy8 = couponPolicyRepository.save(new CouponPolicy(oldCafePolicy.getMaxStampCount(), oldCafePolicy.getReward(), oldCafePolicy.getExpirePeriod()));

        couponRepository.save(new Coupon(LocalDate.now(), tmpCustomer2, cafe3, couponDesign8, couponPolicy8));

        // when 이전 정책일 때 발급받은 고객의 스탬프 적립 쿠폰 조회
        List<CustomerAccumulatingCouponFindResultDto> oldCouponResult = couponService.findAccumulatingCoupon(cafe3.getId(), tmpCustomer1.getId());
        CustomerAccumulatingCouponFindResultDto oldCustomerCoupon = oldCouponResult.get(0);

        // when 새로운 정책일 때 발급받은 고객의 스탬프 적립 쿠폰 조회
        List<CustomerAccumulatingCouponFindResultDto> newCouponResult = couponService.findAccumulatingCoupon(cafe3.getId(), tmpCustomer2.getId());
        CustomerAccumulatingCouponFindResultDto newCustomerCoupon = newCouponResult.get(0);

        assertThat(oldCustomerCoupon.isPrevious()).isTrue();
        assertThat(newCustomerCoupon.isPrevious()).isFalse();
    }

    @Test
    void 새로운_쿠폰을_발급한다() {
        // given, when
        Long savedCouponId = couponService.createCoupon(cafe1.getId(), tmpCustomer1.getId());

        // then
        assertThat(savedCouponId).isNotNull();
    }

    @Test
    void 새로운_쿠폰_발급_시_기존에_사용중인_쿠폰을_EXPIRE한다() {
        // given
        CouponDesign existCouponDesign = couponDesignRepository.save(new CouponDesign("front", "back", "stamp"));
        CouponPolicy existCouponPolicy = couponPolicyRepository.save(new CouponPolicy(10, "아메리카노", 8));
        Coupon existCoupon = couponRepository.save(new Coupon(LocalDate.EPOCH, tmpCustomer1, cafe1, existCouponDesign, existCouponPolicy));

        // when
        couponService.createCoupon(cafe1.getId(), tmpCustomer1.getId());

        // then
        assertThat(existCoupon.getStatus()).isEqualTo(CouponStatus.EXPIRED);
    }

    @Test
    void 없는_회원은_쿠폰을_발급받으려_시도하면_예외를_던진다() {
        // given
        Long notExistCustomerId = Long.MAX_VALUE;

        // when, then
        assertThatThrownBy(() -> couponService.createCoupon(cafe1.getId(), notExistCustomerId))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 없는_카페가_쿠폰을_발급하려_시도하면_예외를_던진다() {
        // given
        Long notExistCafeId = Long.MAX_VALUE;

        // when, then
        assertThatThrownBy(() -> couponService.createCoupon(notExistCafeId, registerCustomer1.getId()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 스탬프_적립_후_쿠폰의_스탬프_개수가_리워드를_받기_위한_스탬프_개수보다_적으면_스탬프_개수만_증가한다() {
        // given, when
        couponService.createStamp(new StampCreateDto(owner1.getId(), registerCustomer2.getId(), coupon6.getId(), 5));

        // then
        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(coupon6.getStatus()).isEqualTo(CouponStatus.ACCUMULATING);
        softAssertions.assertThat(coupon6.getStampCount()).isEqualTo(5);
        softAssertions.assertAll();
    }

    @Test
    void 스탬프_적립_후_쿠폰의_스탬프_개수가_리워드를_받기_위한_스탬프_개수가_같으면_리워드를_생성하고_쿠폰의_상태가_REWARDED가_된다() {
        // given, when
        couponService.createStamp(new StampCreateDto(owner1.getId(), registerCustomer2.getId(), coupon6.getId(), 10));

        // then
        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(coupon6.getStatus()).isEqualTo(CouponStatus.REWARDED);
        softAssertions.assertThat(coupon6.getStampCount()).isEqualTo(10);
        softAssertions.assertThat(rewardRepository.findAllByCustomerIdAndCafeIdAndUsed(registerCustomer2.getId(), cafe1.getId(), false).size()).isEqualTo(1);
        softAssertions.assertThat(couponRepository.findByCafeAndCustomerAndStatus(cafe1, registerCustomer2, CouponStatus.ACCUMULATING)).isEmpty();
        softAssertions.assertAll();
    }

    @ParameterizedTest
    @CsvSource(value = {"11, 1, 1","15, 1, 5", "49, 4, 9", "123, 12, 3"}, delimiter = ',')
    void 스탬프_적립_후_쿠폰의_스탬프_개수가_리워드를_받기_위한_스탬프_개수를_초과하면_리워드를_생성하고_새로운_쿠폰을_발급하고_기존_쿠폰의_상태가_REWARDED가_된다(
            int earningStampCount, int expectedRewardCount, int expectRestStampCount) {
        // given, when
        couponService.createStamp(new StampCreateDto(owner1.getId(), registerCustomer2.getId(), coupon6.getId(), earningStampCount));

        List<Coupon> usingCoupons = couponRepository.findByCafeAndCustomerAndStatus(cafe1, registerCustomer2, CouponStatus.ACCUMULATING);
        Coupon usingCoupon = usingCoupons.stream().findAny().get();

        // then
        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(coupon6.getStatus()).isEqualTo(CouponStatus.REWARDED);
        softAssertions.assertThat(coupon6.getStampCount()).isEqualTo(10);
        softAssertions.assertThat(rewardRepository.findAllByCustomerIdAndCafeIdAndUsed(registerCustomer2.getId(), cafe1.getId(), false).size()).isEqualTo(expectedRewardCount);
        softAssertions.assertThat(usingCoupons.size()).isEqualTo(1);
        softAssertions.assertThat(usingCoupon.getStampCount()).isEqualTo(expectRestStampCount);
        softAssertions.assertAll();
    }
}
