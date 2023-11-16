package com.stampcrush.backend.application.visitor.profile;

import com.stampcrush.backend.application.ServiceSliceTest;
import com.stampcrush.backend.entity.user.Customer;
import com.stampcrush.backend.exception.CustomerNotFoundException;
import com.stampcrush.backend.repository.coupon.CouponRepository;
import com.stampcrush.backend.repository.favorites.FavoritesRepository;
import com.stampcrush.backend.repository.reward.RewardRepository;
import com.stampcrush.backend.repository.user.CustomerRepository;
import com.stampcrush.backend.repository.visithistory.VisitHistoryRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;

@ServiceSliceTest
class VisitorCancelMembershipCommandServiceTest {

    @InjectMocks
    private VisitorCancelMembershipCommandService visitorCancelMembershipCommandService;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private RewardRepository rewardRepository;

    @Mock
    private FavoritesRepository favoritesRepository;

    @Mock
    private VisitHistoryRepository visitHistoryRepository;

    @Mock
    private CouponRepository couponRepository;

    @Test
    void 존재하지_않는_회원탈퇴_시_예외() {
        // given
        given(customerRepository.findById(1L))
                .willReturn(Optional.empty());

        // when, then
        assertThatThrownBy(() -> visitorCancelMembershipCommandService.cancelMembership(1L))
                .isInstanceOf(CustomerNotFoundException.class);
    }

    @Test
    void 회원탈퇴() {
        // given
        given(customerRepository.findById(1L))
                .willReturn(Optional.of(Customer.registeredCustomerBuilder().build()));

        // when
        visitorCancelMembershipCommandService.cancelMembership(1L);

        // then
        then(couponRepository).should(times(1)).deleteByCustomer(anyLong());
        then(rewardRepository).should(times(1)).deleteByCustomer(anyLong());
        then(favoritesRepository).should(times(1)).deleteByCustomer(anyLong());
        then(visitHistoryRepository).should(times(1)).deleteByCustomer(anyLong());
        then(customerRepository).should(times(1)).delete(any());
    }
}
