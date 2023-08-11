package com.stampcrush.backend.application.manager.cafe;

import com.stampcrush.backend.application.ServiceSliceTest;
import com.stampcrush.backend.application.manager.cafe.dto.CafeCouponSettingDto;
import com.stampcrush.backend.exception.CafeNotFoundException;
import com.stampcrush.backend.repository.cafe.CafeCouponDesignRepository;
import com.stampcrush.backend.repository.cafe.CafePolicyRepository;
import com.stampcrush.backend.repository.cafe.CafeRepository;
import com.stampcrush.backend.repository.cafe.CafeStampCoordinateRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;
import java.util.Optional;

import static com.stampcrush.backend.fixture.CafeFixture.GITCHAN_CAFE;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.when;

@ServiceSliceTest
class ManagerCafeCouponSettingCommandServiceTest {

    private static final CafeCouponSettingDto CAFE_COUPON_SETTING_DTO = CafeCouponSettingDto.of(
            "frontImageUrl",
            "backImageUrl",
            "stampImageUrl",
            List.of(List.of(1, 1, 1), List.of(1, 2, 2)),
            2,
            "reward",
            6
    );

    @InjectMocks
    private ManagerCafeCouponSettingCommandService managerCafeCouponSettingCommandService;

    @Mock
    private CafeRepository cafeRepository;

    @Mock
    private CafeCouponDesignRepository cafeCouponDesignRepository;

    @Mock
    private CafePolicyRepository cafePolicyRepository;

    @Mock
    private CafeStampCoordinateRepository cafeStampCoordinateRepository;

    @Test
    void 쿠폰_세팅을_업데이트할_때_카페_정보가_존재하지_않으면_예외가_발생한다() {
        long cafeId = 1L;

        when(cafeRepository.findById(cafeId))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> managerCafeCouponSettingCommandService.updateCafeCouponSetting(cafeId, CAFE_COUPON_SETTING_DTO))
                .isInstanceOf(CafeNotFoundException.class);
    }

    @Test
    void 쿠폰_세팅을_업데이트할_수_있다() {
        long cafeId = 1L;

        when(cafeRepository.findById(cafeId))
                .thenReturn(Optional.of(GITCHAN_CAFE));

        assertDoesNotThrow(() -> managerCafeCouponSettingCommandService.updateCafeCouponSetting(cafeId, CAFE_COUPON_SETTING_DTO));
    }
}
