package com.stampcrush.backend.api.visitor.reward.response;

import com.stampcrush.backend.application.visitor.reward.dto.VisitorRewardsFindResultDto;
import com.stampcrush.backend.entity.reward.Reward;
import com.stampcrush.backend.fixture.CafeFixture;
import com.stampcrush.backend.fixture.CustomerFixture;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class VisitorRewardsFindResponseTest {

    @Test
    void 사용_가능한_리워드는_usedAt이_null이다() {
        Reward reward = new Reward(
                LocalDateTime.now(),
                null,
                1L,
                "아메리카노",
                false,
                CustomerFixture.REGISTER_CUSTOMER_GITCHAN_SAVED,
                CafeFixture.GITCHAN_CAFE
        );

        VisitorRewardsFindResultDto dto = VisitorRewardsFindResultDto.from(reward);

        VisitorRewardsFindResponse response = VisitorRewardsFindResponse.from(List.of(dto));
        VisitorRewardsFindResponse.VisitorRewardFindResponse rewardResponse = response.getRewards().get(0);

        assertAll(
                () -> assertDoesNotThrow(() -> VisitorRewardsFindResponse.from(List.of(dto))),
                () -> assertThat(rewardResponse.getUsedAt()).isNull()
        );
    }
}
