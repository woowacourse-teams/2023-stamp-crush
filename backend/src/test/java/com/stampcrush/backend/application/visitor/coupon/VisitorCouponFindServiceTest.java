package com.stampcrush.backend.application.visitor.coupon;

import com.stampcrush.backend.application.ServiceSliceTest;
import com.stampcrush.backend.application.visitor.coupon.dto.CustomerCouponFindResultDto;
import com.stampcrush.backend.application.visitor.favorites.VisitorFavoritesFindService;
import com.stampcrush.backend.entity.coupon.Coupon;
import com.stampcrush.backend.entity.coupon.CouponStatus;
import com.stampcrush.backend.entity.user.RegisterCustomer;
import com.stampcrush.backend.exception.CustomerNotFoundException;
import com.stampcrush.backend.fixture.CustomerFixture;
import com.stampcrush.backend.repository.coupon.CouponRepository;
import com.stampcrush.backend.repository.coupon.CouponStampCoordinateRepository;
import com.stampcrush.backend.repository.user.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.stampcrush.backend.fixture.CouponFixture.GITCHAN_CAFE_COUPON;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;

@ServiceSliceTest
class VisitorCouponFindServiceTest {

    @InjectMocks
    private VisitorCouponFindService visitorCouponFindService;

    @Mock
    private VisitorFavoritesFindService visitorFavoritesFindService;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private CouponRepository couponRepository;

    @Mock
    private CouponStampCoordinateRepository couponStampCoordinateRepository;

    @Test
    void 하나의_카페당_하나의_쿠폰을_조회할_때_고객정보가_없으면_예외처리한다() {
        long customerId = 1L;

        when(customerRepository.findById(customerId))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> visitorCouponFindService.findOneCouponForOneCafe(customerId))
                .isInstanceOf(CustomerNotFoundException.class);
    }

    @Test
    void 하나의_카페당_하나의_쿠폰을_조회할_때_ACCUMULATING_상태의_쿠폰이_없으면_아무것도_조회하지_않는다() {
        long customerId = 1L;
        RegisterCustomer customer = CustomerFixture.REGISTER_CUSTOMER_GITCHAN;

        when(customerRepository.findById(customerId))
                .thenReturn(Optional.of(customer));

        when(couponRepository.findByCustomerAndStatus(customer, CouponStatus.ACCUMULATING))
                .thenReturn(new ArrayList<>());

        assertThat(visitorCouponFindService.findOneCouponForOneCafe(customerId)).isEmpty();
    }

    @Test
    void 하나의_카페당_하나의_쿠폰을_조회할_수_있다() {
        long customerId = 1L;
        RegisterCustomer customer = CustomerFixture.REGISTER_CUSTOMER_GITCHAN;
        Coupon gitchanCoupon = GITCHAN_CAFE_COUPON;

        when(customerRepository.findById(customerId))
                .thenReturn(Optional.of(customer));

        when(couponRepository.findByCustomerAndStatus(customer, CouponStatus.ACCUMULATING))
                .thenReturn(List.of(gitchanCoupon));

        when(visitorFavoritesFindService.findIsFavorites(gitchanCoupon.getCafe(), customer))
                .thenReturn(true);

        assertAll(
                () -> assertThat(visitorCouponFindService.findOneCouponForOneCafe(customerId)).isNotEmpty(),
                () -> assertThat(visitorCouponFindService.findOneCouponForOneCafe(customerId))
                        .usingRecursiveComparison()
                        .isEqualTo(
                                List.of(
                                        CustomerCouponFindResultDto.of(
                                                gitchanCoupon.getCafe(),
                                                gitchanCoupon,
                                                true,
                                                gitchanCoupon.getCouponDesign().getCouponStampCoordinates()
                                        )))
        );
    }
}
