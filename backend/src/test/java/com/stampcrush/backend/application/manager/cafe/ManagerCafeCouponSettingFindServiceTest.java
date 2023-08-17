package com.stampcrush.backend.application.manager.cafe;

import com.stampcrush.backend.application.ServiceSliceTest;
import com.stampcrush.backend.exception.CafeNotFoundException;
import com.stampcrush.backend.exception.CouponNotFoundException;
import com.stampcrush.backend.repository.cafe.CafeRepository;
import com.stampcrush.backend.repository.coupon.CouponRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;

import static com.stampcrush.backend.fixture.CafeFixture.GITCHAN_CAFE;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ServiceSliceTest
public class ManagerCafeCouponSettingFindServiceTest {

    @InjectMocks
    private ManagerCafeCouponSettingFindService managerCafeCouponSettingFindService;

    @Mock
    private CafeRepository cafeRepository;

    @Mock
    private CouponRepository couponRepository;

    @Test
    void 쿠폰_세팅을_조회할_때_카페_정보가_존재하지_않으면_예외가_발생한다() {
        // given
        Long cafeId = 1L;

        when(cafeRepository.findById(cafeId))
                .thenReturn(Optional.empty());

        // when, then
        assertThatThrownBy(() -> managerCafeCouponSettingFindService.findCouponSetting(cafeId, 1L))
                .isInstanceOf(CafeNotFoundException.class);
    }

    @Test
    void 쿠폰_세팅을_조회할_때_쿠폰_디자인이_존재하지_않으면_예외가_발생한다() {
        // given
        Long cafeId = 1L;

        when(cafeRepository.findById(cafeId))
                .thenReturn(Optional.of(GITCHAN_CAFE));
        when(couponRepository.findById(any()))
                .thenReturn(Optional.empty());
        // when, then
        assertThatThrownBy(() -> managerCafeCouponSettingFindService.findCouponSetting(cafeId, 1L))
                .isInstanceOf(CouponNotFoundException.class);
    }
}
