package com.stampcrush.backend.application.visitor.coupon;

import com.stampcrush.backend.application.ServiceSliceTest;
import com.stampcrush.backend.common.KorNamingConverter;
import com.stampcrush.backend.exception.CouponNotFoundException;
import com.stampcrush.backend.repository.coupon.CouponRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ServiceSliceTest
public class VisitorCouponCommandServiceTest {

    @InjectMocks
    private VisitorCouponCommandService visitorCouponCommandService;

    @Mock
    private CouponRepository couponRepository;

    @Test
    void 삭제하려는_쿠폰을_찾지_못할_시_예외를_던진다() {
        // given
        when(couponRepository.findByIdAndCustomerId(anyLong(), anyLong()))
                .thenReturn(Optional.empty());

        // when, then
        assertThatThrownBy(() -> visitorCouponCommandService.deleteCoupon(1L, 1L))
                .isInstanceOf(CouponNotFoundException.class);
    }
}
