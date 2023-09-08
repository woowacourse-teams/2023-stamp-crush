package com.stampcrush.backend.application.manager.coupon;

import com.stampcrush.backend.application.ServiceSliceTest;
import com.stampcrush.backend.application.manager.coupon.dto.StampCreateDto;
import com.stampcrush.backend.entity.cafe.Cafe;
import com.stampcrush.backend.entity.cafe.CafeCouponDesign;
import com.stampcrush.backend.entity.cafe.CafePolicy;
import com.stampcrush.backend.entity.coupon.Coupon;
import com.stampcrush.backend.entity.coupon.CouponPolicy;
import com.stampcrush.backend.entity.coupon.CouponStatus;
import com.stampcrush.backend.entity.user.Customer;
import com.stampcrush.backend.entity.user.Owner;
import com.stampcrush.backend.exception.CafeNotFoundException;
import com.stampcrush.backend.exception.CustomerNotFoundException;
import com.stampcrush.backend.repository.cafe.CafeCouponDesignRepository;
import com.stampcrush.backend.repository.cafe.CafePolicyRepository;
import com.stampcrush.backend.repository.cafe.CafeRepository;
import com.stampcrush.backend.repository.coupon.CouponDesignRepository;
import com.stampcrush.backend.repository.coupon.CouponPolicyRepository;
import com.stampcrush.backend.repository.coupon.CouponRepository;
import com.stampcrush.backend.repository.reward.RewardRepository;
import com.stampcrush.backend.repository.user.CustomerRepository;
import com.stampcrush.backend.repository.user.OwnerRepository;
import com.stampcrush.backend.repository.visithistory.VisitHistoryRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@ServiceSliceTest
public class ManageCouponCommandServiceTest {

    @InjectMocks
    private ManagerCouponCommandService managerCouponCommandService;

    @Mock
    private CouponRepository couponRepository;

    @Mock
    private CafeRepository cafeRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private CafeCouponDesignRepository cafeCouponDesignRepository;

    @Mock
    private CafePolicyRepository cafePolicyRepository;

    @Mock
    private CouponDesignRepository couponDesignRepository;

    @Mock
    private CouponPolicyRepository couponPolicyRepository;

    @Mock
    private OwnerRepository ownerRepository;

    @Mock
    private RewardRepository rewardRepository;

    @Mock
    private VisitHistoryRepository visitHistoryRepository;

    private static Cafe cafe;
    private static Customer customer;
    private static CouponPolicy couponPolicy;

    @BeforeAll
    static void setUp() {
        cafe = new Cafe(1L, "name", "road", "detailAddress", "phone", null);
        customer = Customer.temporaryCustomerBuilder()
                .id(1L)
                .phoneNumber("01012345678")
                .build();
        couponPolicy = new CouponPolicy(10, "reward", 6);
    }

    @Test
    void 적립중인_쿠폰이_있으면_새로운_쿠폰을_발급하고_기존_쿠폰은_만료된다() {
        // given
        Coupon currentCoupon = new Coupon(LocalDate.EPOCH, customer, cafe, null, couponPolicy);
        given(customerRepository.findById(anyLong()))
                .willReturn(Optional.of(customer));
        given(cafeRepository.findById(anyLong()))
                .willReturn(Optional.of(cafe));
        given(cafePolicyRepository.findByCafe(any()))
                .willReturn(Optional.of(new CafePolicy(10, "reward", 6, false, cafe)));
        given(cafeCouponDesignRepository.findByCafe(any()))
                .willReturn(Optional.of(new CafeCouponDesign("front", "back", "stamp", false, null)));
        given(couponRepository.findByCafeAndCustomerAndStatus(any(), any(), any()))
                .willReturn(List.of(currentCoupon));

        // when
        managerCouponCommandService.createCoupon(1L, 1L);

        // then
        then(couponRepository).should(times(1)).save(any());
        then(couponDesignRepository).should(times(1)).save(any());
        then(couponPolicyRepository).should(times(1)).save(any());
        assertThat(currentCoupon.getStatus()).isEqualTo(CouponStatus.EXPIRED);
    }

    @Test
    void 적립중인_쿠폰이_없으면_새로운_쿠폰만_발급한다() {
        // given
        given(customerRepository.findById(anyLong()))
                .willReturn(Optional.of(customer));
        given(cafeRepository.findById(anyLong()))
                .willReturn(Optional.of(cafe));
        given(cafePolicyRepository.findByCafe(any()))
                .willReturn(Optional.of(new CafePolicy(10, "reward", 6, false, cafe)));
        given(cafeCouponDesignRepository.findByCafe(any()))
                .willReturn(Optional.of(new CafeCouponDesign("front", "back", "stamp", false, null)));
        given(couponRepository.findByCafeAndCustomerAndStatus(any(), any(), any()))
                .willReturn(Collections.emptyList());

        // when
        managerCouponCommandService.createCoupon(1L, 1L);

        // then
        then(couponRepository).should(times(1)).save(any());
        then(couponDesignRepository).should(times(1)).save(any());
        then(couponPolicyRepository).should(times(1)).save(any());
    }

    @Test
    void 존재하지_않는_회원이_쿠폰을_발급받으려_하면_예외발생() {
        // given, when
        given(customerRepository.findById(anyLong()))
                .willReturn(Optional.empty());

        // then
        assertThatThrownBy(() -> managerCouponCommandService.createCoupon(1L, 1L))
                .isInstanceOf(CustomerNotFoundException.class);
    }

    @Test
    void 존재하지_않는_카페가_쿠폰을_발급하려고_하면_예외발생() {
        // given, when
        given(customerRepository.findById(anyLong()))
                .willReturn(Optional.of(customer));
        given(cafeRepository.findById(anyLong()))
                .willReturn(Optional.empty());

        // then
        assertThatThrownBy(() -> managerCouponCommandService.createCoupon(1L, 1L))
                .isInstanceOf(CafeNotFoundException.class);
    }

    @Test
    void 쿠폰_발급_시_카페의_정책이_존재하지_않으면_예외발생() {
        // given, when
        given(customerRepository.findById(anyLong()))
                .willReturn(Optional.of(customer));
        given(cafeRepository.findById(anyLong()))
                .willReturn(Optional.of(cafe));
        given(cafePolicyRepository.findByCafe(any()))
                .willReturn(Optional.empty());

        // then
        assertThatThrownBy(() -> managerCouponCommandService.createCoupon(1L, 1L))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 쿠폰_발급_시_카페의_쿠폰_디자인이_없으면_예외발생() {
        // given, when
        given(customerRepository.findById(anyLong()))
                .willReturn(Optional.of(customer));
        given(cafeRepository.findById(anyLong()))
                .willReturn(Optional.of(cafe));
        given(cafePolicyRepository.findByCafe(any()))
                .willReturn(Optional.of(new CafePolicy(10, "reward", 6, false, cafe)));
        given(cafeCouponDesignRepository.findByCafe(any()))
                .willReturn(Optional.empty());

        // then
        assertThatThrownBy(() -> managerCouponCommandService.createCoupon(1L, 1L))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 추가_적립한_스탬프로_리워드를_받기_위한_스탬프_개수보다_적으면_스탬프만_적립한다() {
        // given, when
        int maxStampCount = 10;
        Coupon currentCoupon = new Coupon(LocalDate.EPOCH, customer, cafe, null, couponPolicy);
        스탬프_적립을_위해_필요한_엔티티를_조회한다(maxStampCount, currentCoupon);

        StampCreateDto stampCreateDto = new StampCreateDto(1L, 1L, 1L, 1);
        managerCouponCommandService.createStamp(stampCreateDto);

        // then
        then(rewardRepository).should(times(0)).save(any());
        then(couponRepository).should(times(0)).save(any());
        then(visitHistoryRepository).should(times(1)).save(any());
        assertThat(currentCoupon.getStatus()).isEqualTo(CouponStatus.ACCUMULATING);
    }

    @Test
    void 추가_적립한_스탬프로_리워드를_받기_위한_스탬프_개수와_같으면_리워드를_생성한다() {
        // given, when
        int maxStampCount = 10;
        Coupon currentCoupon = new Coupon(LocalDate.EPOCH, customer, cafe, null, couponPolicy);
        스탬프_적립을_위해_필요한_엔티티를_조회한다(maxStampCount, currentCoupon);

        StampCreateDto stampCreateDto = new StampCreateDto(1L, 1L, 1L, maxStampCount);
        managerCouponCommandService.createStamp(stampCreateDto);

        // then
        then(rewardRepository).should(times(1)).save(any());
        then(couponRepository).should(times(0)).save(any());
        then(visitHistoryRepository).should(times(1)).save(any());
        assertThat(currentCoupon.getStatus()).isEqualTo(CouponStatus.REWARDED);
    }

    @Test
    void 추가_적립한_스탬프로_리워드를_받기_위한_스탬프_개수를_초과하면_리워드를_생성하고_새로운_쿠폰에_나머지_스탬프가_찍힌다() {
        // given, when
        int maxStampCount = 10;
        Coupon currentCoupon = new Coupon(LocalDate.EPOCH, customer, cafe, null, couponPolicy);
        스탬프_적립을_위해_필요한_엔티티를_조회한다(maxStampCount, currentCoupon);

        StampCreateDto stampCreateDto = new StampCreateDto(1L, 1L, 1L, maxStampCount * 2 + 2);
        managerCouponCommandService.createStamp(stampCreateDto);

        // then
        then(rewardRepository).should(times(2)).save(any());
        then(couponRepository).should(times(2)).save(any());
        then(visitHistoryRepository).should(times(1)).save(any());
    }

    @Test
    void 추가_적립한_스탬프로_리워드를_받기_위한_스탬프_개수를_초과하면_리워드를_생성하고_새로운_쿠폰에_찍을_스탬프가_없다면_쿠폰을_발급하지_않는다() {
        // given, when
        int maxStampCount = 10;
        Coupon currentCoupon = new Coupon(LocalDate.EPOCH, customer, cafe, null, couponPolicy);
        스탬프_적립을_위해_필요한_엔티티를_조회한다(maxStampCount, currentCoupon);

        StampCreateDto stampCreateDto = new StampCreateDto(1L, 1L, 1L, maxStampCount * 2);
        managerCouponCommandService.createStamp(stampCreateDto);

        // then
        then(rewardRepository).should(times(2)).save(any());
        then(couponRepository).should(times(1)).save(any());
        then(visitHistoryRepository).should(times(1)).save(any());
    }

    @Test
    void 기존에_스탬프가_있는_쿠폰에_추가_적립한_스탬프로_리워드를_받기_위한_스탬프_개수보다_적으면_스탬프만_적립한다() {
        // given
        Coupon currentCoupon = new Coupon(LocalDate.EPOCH, customer, cafe, null, couponPolicy);
        currentCoupon.accumulate(4);
        int maxStampCount = 10;
        스탬프_적립을_위해_필요한_엔티티를_조회한다(maxStampCount, currentCoupon);

        // when
        StampCreateDto stampCreateDto = new StampCreateDto(1L, 1L, 1L, 3);
        managerCouponCommandService.createStamp(stampCreateDto);

        // then
        then(rewardRepository).should(times(0)).save(any());
        then(couponRepository).should(times(0)).save(any());
        then(visitHistoryRepository).should(times(1)).save(any());
        assertThat(currentCoupon.getStatus()).isEqualTo(CouponStatus.ACCUMULATING);
        assertThat(currentCoupon.getStampCount()).isEqualTo(7);
    }

    @Test
    void 기존에_스탬프가_있는_쿠폰에_추가_적립한_스탬프로_리워드를_받기_위한_스탬프_개수와_같으면_리워드를_생성한다() {
        // givem
        Coupon currentCoupon = new Coupon(LocalDate.EPOCH, customer, cafe, null, couponPolicy);
        currentCoupon.accumulate(4);
        int maxStampCount = 10;
        스탬프_적립을_위해_필요한_엔티티를_조회한다(maxStampCount, currentCoupon);

        // when
        int earningStamp = 6;
        StampCreateDto stampCreateDto = new StampCreateDto(1L, 1L, 1L, earningStamp);
        managerCouponCommandService.createStamp(stampCreateDto);

        // then
        then(rewardRepository).should(times(1)).save(any());
        then(couponRepository).should(times(0)).save(any());
        then(visitHistoryRepository).should(times(1)).save(any());
        assertThat(currentCoupon.getStatus()).isEqualTo(CouponStatus.REWARDED);
        assertThat(currentCoupon.getStampCount()).isEqualTo(10);
    }

    @Test
    void 기존에_스탬프가_있는_쿠폰에_추가_적립한_스탬프로_리워드를_받기_위한_스탬프_개수를_초과하면_리워드를_생성하고_새로운_쿠폰에_나머지_스탬프가_찍힌다() {
        // given
        Coupon currentCoupon = new Coupon(LocalDate.EPOCH, customer, cafe, null, couponPolicy);
        currentCoupon.accumulate(4);
        int maxStampCount = 10;
        스탬프_적립을_위해_필요한_엔티티를_조회한다(maxStampCount, currentCoupon);

        // when
        int earningStamp = 17;
        StampCreateDto stampCreateDto = new StampCreateDto(1L, 1L, 1L, earningStamp);
        managerCouponCommandService.createStamp(stampCreateDto);

        // then
        then(rewardRepository).should(times(2)).save(any());
        then(couponRepository).should(times(2)).save(any());
        then(visitHistoryRepository).should(times(1)).save(any());
    }

    @Test
    void 기존에_스탬프가_있는_쿠폰에_추가_적립한_스탬프로_리워드를_받기_위한_스탬프_개수를_초과하면_리워드를_생성하고_새로운_쿠폰에_찍을_스탬프가_없다면_쿠폰을_발급하지_않는다() {
        // given, when
        Coupon currentCoupon = new Coupon(LocalDate.EPOCH, customer, cafe, null, couponPolicy);
        currentCoupon.accumulate(4);
        int maxStampCount = 10;
        스탬프_적립을_위해_필요한_엔티티를_조회한다(maxStampCount, currentCoupon);

        StampCreateDto stampCreateDto = new StampCreateDto(1L, 1L, 1L, maxStampCount * 2 - 4);
        managerCouponCommandService.createStamp(stampCreateDto);

        // then
        then(rewardRepository).should(times(2)).save(any());
        then(couponRepository).should(times(1)).save(any());
        then(visitHistoryRepository).should(times(1)).save(any());
    }

    private void 스탬프_적립을_위해_필요한_엔티티를_조회한다(int maxStampCount, Coupon coupon) {
        given(ownerRepository.findById(any()))
                .willReturn(Optional.of(new Owner("owner", "id", "pw", "phone")));
        given(customerRepository.findById(any()))
                .willReturn(Optional.of(customer));
        given(cafeRepository.findAllByOwner(any()))
                .willReturn(List.of(cafe));
        given(cafePolicyRepository.findByCafe(any()))
                .willReturn(Optional.of(new CafePolicy(maxStampCount, "reward", 6, false, cafe)));
        given(cafeCouponDesignRepository.findByCafe(any()))
                .willReturn(Optional.of(new CafeCouponDesign("front", "back", "stamp", false, null)));
        given(couponRepository.findById(any()))
                .willReturn(Optional.of(coupon));
    }
}
