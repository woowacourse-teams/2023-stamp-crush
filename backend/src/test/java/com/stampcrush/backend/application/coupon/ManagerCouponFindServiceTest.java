package com.stampcrush.backend.application.coupon;

import com.stampcrush.backend.application.manager.coupon.CustomerCouponStatistics;
import com.stampcrush.backend.application.manager.coupon.ManagerCouponFindService;
import com.stampcrush.backend.application.manager.coupon.dto.CafeCustomerFindResultDto;
import com.stampcrush.backend.entity.cafe.Cafe;
import com.stampcrush.backend.entity.coupon.Coupon;
import com.stampcrush.backend.entity.coupon.CouponPolicy;
import com.stampcrush.backend.entity.user.Customer;
import com.stampcrush.backend.entity.user.TemporaryCustomer;
import com.stampcrush.backend.repository.cafe.CafePolicyRepository;
import com.stampcrush.backend.repository.cafe.CafeRepository;
import com.stampcrush.backend.repository.coupon.CouponRepository;
import com.stampcrush.backend.repository.user.CustomerRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

@SuppressWarnings("NonAsciiCharacters")
@ExtendWith(MockitoExtension.class)
public class ManagerCouponFindServiceTest {

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

    private static Cafe cafe;
    private static Customer customer1;
    private static Customer customer2;
    private static CouponPolicy couponPolicy1;
    private static CouponPolicy couponPolicy2;

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

        LocalDateTime coupon2CreatedAt = LocalDateTime.now();
        LocalDateTime coupon2UpdatedAt = LocalDateTime.now();
        Coupon coupon2 = new Coupon(coupon2CreatedAt, coupon2UpdatedAt, LocalDate.EPOCH, customer2, cafe, null, couponPolicy2);

        given(cafeRepository.findById(anyLong()))
                .willReturn(Optional.of(cafe));
        given(couponRepository.findByCafe(any()))
                .willReturn(List.of(coupon1, coupon2));

        // when
        CustomerCouponStatistics customer1Statics = new CustomerCouponStatistics(0, 0, 0, 10, coupon1CreatedAt);
        CustomerCouponStatistics customer2Statics = new CustomerCouponStatistics(0, 0, 0, 15, coupon2CreatedAt);

        CafeCustomerFindResultDto customer1Result = CafeCustomerFindResultDto.of(customer1, customer1Statics);
        CafeCustomerFindResultDto customer2Result = CafeCustomerFindResultDto.of(customer2, customer2Statics);
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

        given(cafeRepository.findById(anyLong()))
                .willReturn(Optional.of(cafe));
        given(couponRepository.findByCafe(any()))
                .willReturn(List.of(coupon1));

        CustomerCouponStatistics customer1Statics = new CustomerCouponStatistics(0, 0, 0, 0, coupon1CreatedAt);
        CafeCustomerFindResultDto customer1Result = CafeCustomerFindResultDto.of(customer1, customer1Statics);
        List<CafeCustomerFindResultDto> couponsByCafe = managerCouponFindService.findCouponsByCafe(anyLong());

        // then
        assertThat(couponsByCafe).containsExactlyInAnyOrder(customer1Result);
    }
}
