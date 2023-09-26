package com.stampcrush.backend.application.manager.coupon;

import com.stampcrush.backend.application.ServiceSliceTest;
import com.stampcrush.backend.application.manager.coupon.dto.CafeCustomerFindResultDto;
import com.stampcrush.backend.application.manager.coupon.dto.CustomerAccumulatingCouponFindResultDto;
import com.stampcrush.backend.entity.cafe.Cafe;
import com.stampcrush.backend.entity.cafe.CafePolicy;
import com.stampcrush.backend.entity.coupon.Coupon;
import com.stampcrush.backend.entity.coupon.CouponPolicy;
import com.stampcrush.backend.entity.user.Customer;
import com.stampcrush.backend.entity.user.Owner;
import com.stampcrush.backend.entity.visithistory.VisitHistories;
import com.stampcrush.backend.entity.visithistory.VisitHistory;
import com.stampcrush.backend.repository.cafe.CafeRepository;
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
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

@ServiceSliceTest
public class ManagerCouponFindServiceTest {

    private static Cafe cafe;
    private static Owner owner;
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
    private VisitHistoryRepository visitHistoryRepository;

    @Mock
    private OwnerRepository ownerRepository;

    @Mock
    private RewardRepository rewardRepository;

    @BeforeAll
    static void setUp() {
        owner = new Owner(1L, "owner", "ownerId", "ownerPassword", "01010010");
        cafe = new Cafe(1L, "name", "road", "detailAddress", "phone", owner);

        customer1 = Customer.temporaryCustomerBuilder()
                .id(1L)
                .phoneNumber("01012345678")
                .build();
        customer2 = Customer.temporaryCustomerBuilder()
                .id(2L)
                .phoneNumber("01098765432")
                .build();
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

        given(ownerRepository.findById(anyLong()))
                .willReturn(Optional.of(owner));
        given(cafeRepository.findById(anyLong()))
                .willReturn(Optional.of(cafe));
        given(couponRepository.findByCafe(any()))
                .willReturn(List.of(coupon1, coupon2));
        given(visitHistoryRepository.findByCafeAndCustomer(cafe, customer1))
                .willReturn(List.of(visitHistory1));
        given(visitHistoryRepository.findByCafeAndCustomer(cafe, customer2))
                .willReturn(List.of(visitHistory2));

        // when
        VisitHistories customer1VisitHistories = new VisitHistories(List.of(visitHistory1));
        VisitHistories customer2VisitHistories = new VisitHistories(List.of(visitHistory2));
        CustomerCouponStatistics customer1Statics = new CustomerCouponStatistics(0, 10);
        CustomerCouponStatistics customer2Statics = new CustomerCouponStatistics(0, 15);

        CafeCustomerFindResultDto customer1Result = CafeCustomerFindResultDto.of(customer1, customer1Statics, customer1VisitHistories, 0);
        CafeCustomerFindResultDto customer2Result = CafeCustomerFindResultDto.of(customer2, customer2Statics, customer2VisitHistories, 0);
        List<CafeCustomerFindResultDto> couponsByCafe = managerCouponFindService.findCouponsByCafe(owner.getId(), cafe.getId());

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

        given(ownerRepository.findById(anyLong()))
                .willReturn(Optional.of(owner));
        given(cafeRepository.findById(anyLong()))
                .willReturn(Optional.of(cafe));
        given(couponRepository.findByCafe(any()))
                .willReturn(List.of(coupon1));
        given(visitHistoryRepository.findByCafeAndCustomer(cafe, customer1))
                .willReturn(List.of(visitHistory));

        VisitHistories visitHistories = new VisitHistories(List.of(visitHistory));
        CustomerCouponStatistics customer1Statistics = new CustomerCouponStatistics(0, 0);
        CafeCustomerFindResultDto customer1Result = CafeCustomerFindResultDto.of(customer1, customer1Statistics, visitHistories, 0);
        List<CafeCustomerFindResultDto> couponsByCafe = managerCouponFindService.findCouponsByCafe(owner.getId(), cafe.getId());

        // then
        assertThat(couponsByCafe).containsExactlyInAnyOrder(customer1Result);
    }

    @Test
    void 쿠폰_정책이_쿠폰의_카페의_정책과_내용이_같지_않으면_예전_정책의_쿠폰이다() {
        // given
        LocalDateTime coupon1CreatedAt = LocalDateTime.now();
        LocalDateTime coupon1UpdatedAt = LocalDateTime.now();
        Coupon coupon = new Coupon(coupon1CreatedAt, coupon1UpdatedAt, LocalDate.EPOCH, customer1, cafe, null, couponPolicy1);
        given(ownerRepository.findById(anyLong()))
                .willReturn(Optional.of(owner));
        given(cafeRepository.findById(anyLong()))
                .willReturn(Optional.of(cafe));
        given(customerRepository.findById(anyLong()))
                .willReturn(Optional.of(customer1));
        given(couponRepository.findByCafeAndCustomerAndStatus(any(), any(), any()))
                .willReturn(List.of(coupon));
        given(ownerRepository.findById(anyLong()))
                .willReturn(Optional.of(cafe.getOwner()));

        cafe.getPolicies().clear();
        cafe.getPolicies().add(new CafePolicy(10, "americano", 6, false, cafe));

        // when
        List<CustomerAccumulatingCouponFindResultDto> findResult = managerCouponFindService.findAccumulatingCoupon(owner.getId(), 1L, 1L);

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
    void 쿠폰_정책이_쿠폰의_카페의_정책과_내용이_같으면_현재_정책의_쿠폰이다() {
        // given
        LocalDateTime coupon1CreatedAt = LocalDateTime.now();
        LocalDateTime coupon1UpdatedAt = LocalDateTime.now();
        Coupon coupon = new Coupon(coupon1CreatedAt, coupon1UpdatedAt, LocalDate.EPOCH, customer1, cafe, null, couponPolicy1);

        given(ownerRepository.findById(anyLong()))
                .willReturn(Optional.of(owner));
        given(cafeRepository.findById(anyLong()))
                .willReturn(Optional.of(cafe));
        given(customerRepository.findById(anyLong()))
                .willReturn(Optional.of(customer1));
        given(couponRepository.findByCafeAndCustomerAndStatus(any(), any(), any()))
                .willReturn(List.of(coupon));
        given(ownerRepository.findById(anyLong()))
                .willReturn(Optional.of(cafe.getOwner()));

        cafe.getPolicies().clear();
        cafe.getPolicies().add(new CafePolicy(couponPolicy1.getMaxStampCount(), couponPolicy1.getRewardName(), couponPolicy1.getExpiredPeriod(), false, cafe));

        // when
        List<CustomerAccumulatingCouponFindResultDto> findResult = managerCouponFindService.findAccumulatingCoupon(1L, 1L, 1L);

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

        given(ownerRepository.findById(anyLong()))
                .willReturn(Optional.of(owner));
        given(cafeRepository.findById(anyLong()))
                .willReturn(Optional.of(cafe));
        given(couponRepository.findByCafe(any()))
                .willReturn(List.of(coupon1, coupon2));
        given(visitHistoryRepository.findByCafeAndCustomer(cafe, customer1))
                .willReturn(List.of(customer1VisitHistory1, customer1VisitHistory2));
        given(visitHistoryRepository.findByCafeAndCustomer(cafe, customer2))
                .willReturn(List.of(customer2visitHistory1));
        given(ownerRepository.findById(anyLong()))
                .willReturn(Optional.of(cafe.getOwner()));

        // when
        VisitHistories customer1VisitHistories = new VisitHistories(List.of(customer1VisitHistory1, customer1VisitHistory2));
        VisitHistories customer2VisitHistories = new VisitHistories(List.of(customer2visitHistory1));

        CustomerCouponStatistics customer1Statics = new CustomerCouponStatistics(customer1EarningStampCount1 + customer1EarningStampCount2, couponPolicy1.getMaxStampCount());
        CustomerCouponStatistics customer2Statics = new CustomerCouponStatistics(customer2EarningStampCount, couponPolicy2.getMaxStampCount());

        CafeCustomerFindResultDto customer1Result = CafeCustomerFindResultDto.of(customer1, customer1Statics, customer1VisitHistories, 0);
        CafeCustomerFindResultDto customer2Result = CafeCustomerFindResultDto.of(customer2, customer2Statics, customer2VisitHistories, 0);
        List<CafeCustomerFindResultDto> couponsByCafe = managerCouponFindService.findCouponsByCafe(owner.getId(), cafe.getId());

        // then
        assertThat(couponsByCafe).containsExactlyInAnyOrder(customer1Result, customer2Result);
    }

    @Test
    void 카페의_고객_목록_조회_시_최근_방문_일을_계산한다() {
        // given
        int rewardCount = 0;

        LocalDateTime coupon1CreatedAt = LocalDateTime.of(2023, 5, 13, 4, 23);
        LocalDateTime coupon1UpdatedAt = LocalDateTime.of(2023, 5, 13, 4, 23);

        int customer1EarningStampCount1 = 3;
        int customer1EarningStampCount2 = 2;

        Coupon coupon = new Coupon(coupon1CreatedAt, coupon1UpdatedAt, LocalDate.EPOCH, customer1, cafe, null, couponPolicy1);
        coupon.accumulate(customer1EarningStampCount1);
        LocalDateTime firstVisit = LocalDateTime.of(2023, 5, 13, 4, 23);
        VisitHistory customer1VisitHistory1 = new VisitHistory(firstVisit, null, cafe, customer1, customer1EarningStampCount1);

        coupon.accumulate(customer1EarningStampCount2);
        LocalDateTime secondVisit = LocalDateTime.of(2023, 6, 13, 4, 23);
        VisitHistory customer1VisitHistory2 = new VisitHistory(secondVisit, null, cafe, customer1, customer1EarningStampCount2);

        given(ownerRepository.findById(anyLong()))
                .willReturn(Optional.of(owner));
        given(cafeRepository.findById(anyLong()))
                .willReturn(Optional.of(cafe));
        given(couponRepository.findByCafe(any()))
                .willReturn(List.of(coupon));
        given(visitHistoryRepository.findByCafeAndCustomer(cafe, customer1))
                .willReturn(List.of(customer1VisitHistory1, customer1VisitHistory2));
        given(ownerRepository.findById(anyLong()))
                .willReturn(Optional.of(cafe.getOwner()));

        // when
        VisitHistories visitHistories = new VisitHistories(List.of(customer1VisitHistory1, customer1VisitHistory2));

        CustomerCouponStatistics customer1Statics = new CustomerCouponStatistics(customer1EarningStampCount1 + customer1EarningStampCount2, couponPolicy1.getMaxStampCount());

        CafeCustomerFindResultDto customer1Result = CafeCustomerFindResultDto.of(customer1, customer1Statics, visitHistories, 0);
        List<CafeCustomerFindResultDto> couponsByCafe = managerCouponFindService.findCouponsByCafe(owner.getId(), cafe.getId());

        // then
        assertThat(couponsByCafe).containsExactlyInAnyOrder(customer1Result);
    }
}
