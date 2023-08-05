package com.stampcrush.backend.application.coupon;

import com.stampcrush.backend.application.manager.coupon.ManagerCouponCommandService;
import com.stampcrush.backend.application.manager.coupon.dto.StampCreateDto;
import com.stampcrush.backend.entity.cafe.Cafe;
import com.stampcrush.backend.entity.cafe.CafeCouponDesign;
import com.stampcrush.backend.entity.cafe.CafePolicy;
import com.stampcrush.backend.entity.coupon.Coupon;
import com.stampcrush.backend.entity.coupon.CouponPolicy;
import com.stampcrush.backend.entity.user.Customer;
import com.stampcrush.backend.entity.user.Owner;
import com.stampcrush.backend.entity.user.TemporaryCustomer;
import com.stampcrush.backend.repository.cafe.CafeCouponDesignRepository;
import com.stampcrush.backend.repository.cafe.CafePolicyRepository;
import com.stampcrush.backend.repository.cafe.CafeRepository;
import com.stampcrush.backend.repository.coupon.CouponDesignRepository;
import com.stampcrush.backend.repository.coupon.CouponPolicyRepository;
import com.stampcrush.backend.repository.coupon.CouponRepository;
import com.stampcrush.backend.repository.reward.RewardRepository;
import com.stampcrush.backend.repository.user.CustomerRepository;
import com.stampcrush.backend.repository.user.OwnerRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@SuppressWarnings("NonAsciiCharacters")
@ExtendWith(MockitoExtension.class)
public class ManageCouponCommandServiceTest1 {

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

    private static Cafe cafe;
    private static Customer customer;
    private static CouponPolicy couponPolicy;

    @BeforeAll
    static void setUp() {
        cafe = new Cafe(1L, "name", "road", "detailAddress", "phone", null);
        customer = new TemporaryCustomer(1L, "name", "phone");
        couponPolicy = new CouponPolicy(10, "reward", 6);
    }

    @Test
    void 기존에_적립된_스탬프가_있을_때_추가_적립한_스탬프로_리워드를_받기_위한_스탬프_개수보다_적으면_스탬프만_적립한다() {
        // given
        StampCreateDto stampCreateDto = new StampCreateDto(1L, 1L, 1L, 1);

        // when
        given(ownerRepository.findById(any()))
                .willReturn(Optional.of(new Owner("owner", "id", "pw", "phone")));
        given(customerRepository.findById(any()))
                .willReturn(Optional.of(customer));
        given(cafeRepository.findAllByOwner(any()))
                .willReturn(List.of(cafe));
        given(cafePolicyRepository.findByCafe(any()))
                .willReturn(Optional.of(new CafePolicy(10, "reward", 6, false, cafe)));
        given(cafeCouponDesignRepository.findByCafe(any()))
                .willReturn(Optional.of(new CafeCouponDesign("front", "back", "stamp", false, null)));
        given(couponRepository.findById(any()))
                .willReturn(Optional.of(new Coupon(LocalDate.EPOCH, customer, cafe, null, couponPolicy)));

        managerCouponCommandService.createStamp(stampCreateDto);

        // then
        then(rewardRepository).should(times(0)).save(any());
    }

}
