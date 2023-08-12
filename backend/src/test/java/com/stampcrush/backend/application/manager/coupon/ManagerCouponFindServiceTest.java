package com.stampcrush.backend.application.manager.coupon;

import com.stampcrush.backend.application.manager.coupon.dto.CafeCustomerFindResultDto;
import com.stampcrush.backend.application.manager.coupon.dto.CustomerAccumulatingCouponFindResultDto;
import com.stampcrush.backend.common.KorNamingConverter;
import com.stampcrush.backend.entity.cafe.Cafe;
import com.stampcrush.backend.entity.cafe.CafePolicy;
import com.stampcrush.backend.entity.coupon.Coupon;
import com.stampcrush.backend.entity.coupon.CouponPolicy;
import com.stampcrush.backend.entity.user.Customer;
import com.stampcrush.backend.entity.user.TemporaryCustomer;
import com.stampcrush.backend.entity.visithistory.VisitHistory;
import com.stampcrush.backend.repository.cafe.CafePolicyRepository;
import com.stampcrush.backend.repository.cafe.CafeRepository;
import com.stampcrush.backend.repository.coupon.CouponRepository;
import com.stampcrush.backend.repository.user.CustomerRepository;
import com.stampcrush.backend.repository.visithistory.VisitHistoryRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

@KorNamingConverter
@ExtendWith(MockitoExtension.class)
public class ManagerCouponFindServiceTest {

    private static Cafe cafe;
    private static Customer customer1;
    private static Customer customer2;
    private static CouponPolicy couponPolicy1;
    private static CouponPolicy couponPolicy2;
    @InjectMocks
    private ManagerCouponFindService managerCouponFindService;
    @Mock
    private CouponRepository couponRepository;
    @Mock
    private CafeRepository cafeRepository;
    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private CafePolicyRepository cafePolicyRepository;
    @Mock
    private VisitHistoryRepository visitHistoryRepository;

    @BeforeAll
    static void setUp() {
        cafe = new Cafe(1L, "name", "road", "detailAddress", "phone", null);
        customer1 = new TemporaryCustomer(1L, "customer1", "phone");
        customer2 = new TemporaryCustomer(2L, "customer2", "phone");
        couponPolicy1 = new CouponPolicy(10, "reward", 6);
        couponPolicy2 = new CouponPolicy(15, "reward", 6);
    }

    @Test
    void 카페의_고객_중_적립중인_쿠폰이_있으면_maxStampCount는_해당_쿠폰에_맞는_maxStampCount다() {
        // given
        LocalDateTime coupon1CreatedAt = LocalDateTime.now();
        LocalDateTime coupon1UpdatedAt = LocalDateTime.now();
        Coupon coupon1 = new Coupon(coupon1CreatedAt, coupon1UpdatedAt, LocalDate.EPOCH, customer1, cafe, null, couponPolicy1);
        VisitHistory visitHistory1 = new VisitHistory(coupon1CreatedAt, null, cafe, customer1, 3);

        LocalDateTime coupon2CreatedAt = LocalDateTime.now();
        LocalDateTime coupon2UpdatedAt = LocalDateTime.now();
        Coupon coupon2 = new Coupon(coupon2CreatedAt, coupon2UpdatedAt, LocalDate.EPOCH, customer2, cafe, null, couponPolicy2);
        VisitHistory visitHistory2 = new VisitHistory(coupon2CreatedAt, null, cafe, customer2, 5);

        given(cafeRepository.findById(anyLong()))
                .willReturn(Optional.of(cafe));
        given(couponRepository.findByCafe(any()))
                .willReturn(List.of(coupon1, coupon2));
        given(visitHistoryRepository.findByCafeAndCustomer(cafe, customer1))
                .willReturn(List.of(visitHistory1));
        given(visitHistoryRepository.findByCafeAndCustomer(cafe, customer2))
                .willReturn(List.of(visitHistory2));

        // when
        CustomerCouponStatistics customer1Statics = new CustomerCouponStatistics(0, 0, 10);
        CustomerCouponStatistics customer2Statics = new CustomerCouponStatistics(0, 0, 15);

        CafeCustomerFindResultDto customer1Result = CafeCustomerFindResultDto.of(customer1, customer1Statics, 1, coupon1CreatedAt);
        CafeCustomerFindResultDto customer2Result = CafeCustomerFindResultDto.of(customer2, customer2Statics, 1, coupon2CreatedAt);
        List<CafeCustomerFindResultDto> couponsByCafe = managerCouponFindService.findCouponsByCafe(anyLong());

        // then
        assertThat(couponsByCafe).containsExactlyInAnyOrder(customer1Result, customer2Result);
    }

    @Test
    void 카페의_고객_중_적립중인_쿠폰이_없으면_maxStampCount는_0이다() {
        // given, when
        LocalDateTime coupon1CreatedAt = LocalDateTime.now();
        LocalDateTime coupon1UpdatedAt = LocalDateTime.now();
        Coupon coupon1 = new Coupon(coupon1CreatedAt, coupon1UpdatedAt, LocalDate.EPOCH, customer1, cafe, null, couponPolicy1);
        coupon1.expire();
        VisitHistory visitHistory = new VisitHistory(coupon1CreatedAt, null, cafe, customer1, 3);

        given(cafeRepository.findById(anyLong()))
                .willReturn(Optional.of(cafe));
        given(couponRepository.findByCafe(any()))
                .willReturn(List.of(coupon1));
        given(visitHistoryRepository.findByCafeAndCustomer(cafe, customer1))
                .willReturn(List.of(visitHistory));

        CustomerCouponStatistics customer1Statistics = new CustomerCouponStatistics(0, 0, 0);
        CafeCustomerFindResultDto customer1Result = CafeCustomerFindResultDto.of(customer1, customer1Statistics, 1, coupon1CreatedAt);
        List<CafeCustomerFindResultDto> couponsByCafe = managerCouponFindService.findCouponsByCafe(anyLong());

        // then
        assertThat(couponsByCafe).containsExactlyInAnyOrder(customer1Result);
    }

    @Test
    void 쿠폰_생성_시간보다_늦게_생성된_쿠폰_정책이_있으면_예전_정책의_쿠폰이다() {
        // given
        LocalDateTime coupon1CreatedAt = LocalDateTime.now();
        LocalDateTime coupon1UpdatedAt = LocalDateTime.now();
        Coupon coupon = new Coupon(coupon1CreatedAt, coupon1UpdatedAt, LocalDate.EPOCH, customer1, cafe, null, couponPolicy1);
        given(cafeRepository.findById(anyLong()))
                .willReturn(Optional.of(cafe));
        given(customerRepository.findById(anyLong()))
                .willReturn(Optional.of(customer1));
        given(couponRepository.findByCafeAndCustomerAndStatus(any(), any(), any()))
                .willReturn(List.of(coupon));

        // when
        given(cafePolicyRepository.findByCafeAndCreatedAtGreaterThan(any(), any()))
                .willReturn(List.of(new CafePolicy(10, "reward", 10, true, cafe)));
        List<CustomerAccumulatingCouponFindResultDto> findResult = managerCouponFindService.findAccumulatingCoupon(1L, 1L);

        // then
        CustomerAccumulatingCouponFindResultDto expected = new CustomerAccumulatingCouponFindResultDto(1L,
                customer1.getId(),
                customer1.getNickname(),
                0,
                LocalDateTime.now(),
                true,
                10);
        assertThat(findResult).usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "expireDate")
                .containsExactlyInAnyOrder(expected);
    }

    @Test
    void 쿠폰_생성_시간보다_늦게_생성된_쿠폰_정책이_없으면_최신_정책의_쿠폰이다() {
        // given
        LocalDateTime coupon1CreatedAt = LocalDateTime.now();
        LocalDateTime coupon1UpdatedAt = LocalDateTime.now();
        Coupon coupon = new Coupon(coupon1CreatedAt, coupon1UpdatedAt, LocalDate.EPOCH, customer1, cafe, null, couponPolicy1);
        given(cafeRepository.findById(anyLong()))
                .willReturn(Optional.of(cafe));
        given(customerRepository.findById(anyLong()))
                .willReturn(Optional.of(customer1));
        given(couponRepository.findByCafeAndCustomerAndStatus(any(), any(), any()))
                .willReturn(List.of(coupon));

        // when
        given(cafePolicyRepository.findByCafeAndCreatedAtGreaterThan(any(), any()))
                .willReturn(Collections.emptyList());
        List<CustomerAccumulatingCouponFindResultDto> findResult = managerCouponFindService.findAccumulatingCoupon(1L, 1L);

        // then
        CustomerAccumulatingCouponFindResultDto expected = new CustomerAccumulatingCouponFindResultDto(1L,
                customer1.getId(),
                customer1.getNickname(),
                0,
                LocalDateTime.now(),
                false,
                10);
        assertThat(findResult).usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "expireDate")
                .containsExactlyInAnyOrder(expected);
    }

    @Test
    void 카페의_고객_목록_조회_시_방문횟수와_첫_방문일을_계산한다() {
        // given

        int rewardCount = 0;

        LocalDateTime coupon1CreatedAt = LocalDateTime.now();
        LocalDateTime coupon1UpdatedAt = LocalDateTime.now();

        int customer1EarningStampCount1 = 3;
        int customer1EarningStampCount2 = 2;

        Coupon coupon1 = new Coupon(coupon1CreatedAt, coupon1UpdatedAt, LocalDate.EPOCH, customer1, cafe, null, couponPolicy1);
        coupon1.accumulate(customer1EarningStampCount1);
        VisitHistory customer1VisitHistory1 = new VisitHistory(coupon1CreatedAt, null, cafe, customer1, customer1EarningStampCount1);
        coupon1.accumulate(customer1EarningStampCount2);
        VisitHistory customer1VisitHistory2 = new VisitHistory(coupon1UpdatedAt, null, cafe, customer1, customer1EarningStampCount2);

        LocalDateTime coupon2CreatedAt = LocalDateTime.now();
        LocalDateTime coupon2UpdatedAt = LocalDateTime.now();

        int customer2EarningStampCount = 5;

        Coupon coupon2 = new Coupon(coupon2CreatedAt, coupon2UpdatedAt, LocalDate.EPOCH, customer2, cafe, null, couponPolicy2);
        coupon2.accumulate(customer2EarningStampCount);
        VisitHistory customer2visitHistory1 = new VisitHistory(coupon2CreatedAt, null, cafe, customer2, customer2EarningStampCount);

        given(cafeRepository.findById(anyLong()))
                .willReturn(Optional.of(cafe));
        given(couponRepository.findByCafe(any()))
                .willReturn(List.of(coupon1, coupon2));
        given(visitHistoryRepository.findByCafeAndCustomer(cafe, customer1))
                .willReturn(List.of(customer1VisitHistory1, customer1VisitHistory2));
        given(visitHistoryRepository.findByCafeAndCustomer(cafe, customer2))
                .willReturn(List.of(customer2visitHistory1));

        // when
        CustomerCouponStatistics customer1Statics = new CustomerCouponStatistics(customer1EarningStampCount1 + customer1EarningStampCount2
                , rewardCount, couponPolicy1.getMaxStampCount());
        CustomerCouponStatistics customer2Statics = new CustomerCouponStatistics(customer2EarningStampCount, rewardCount,
                couponPolicy2.getMaxStampCount());

        CafeCustomerFindResultDto customer1Result = CafeCustomerFindResultDto.of(customer1, customer1Statics, 2, coupon1CreatedAt);
        CafeCustomerFindResultDto customer2Result = CafeCustomerFindResultDto.of(customer2, customer2Statics, 1, coupon2CreatedAt);
        List<CafeCustomerFindResultDto> couponsByCafe = managerCouponFindService.findCouponsByCafe(anyLong());

        // then
        assertThat(couponsByCafe).containsExactlyInAnyOrder(customer1Result, customer2Result);
    }
}
