package com.stampcrush.backend.entity.cafe;

import com.stampcrush.backend.common.KorNamingConverter;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

@KorNamingConverter
class CafePolicyTest {

    @Test
    void 보상_스탬프_개수를_토대로_스탬프에_대해_몇_개의_보상이_발급되는지_계산한다() {
        // given, when
        CafePolicy cafePolicy = new CafePolicy(10, "reward", 6, false, null);
        int rewardCouponCount = cafePolicy.calculateRewardCouponCount(76);

        // then
        Assertions.assertThat(rewardCouponCount).isEqualTo(7);
    }
}
