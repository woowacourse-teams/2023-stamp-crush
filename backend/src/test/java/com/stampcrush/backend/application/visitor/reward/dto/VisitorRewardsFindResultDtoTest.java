package com.stampcrush.backend.application.visitor.reward.dto;

import com.stampcrush.backend.entity.reward.Reward;
import com.stampcrush.backend.fixture.CafeFixture;
import com.stampcrush.backend.fixture.CustomerFixture;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class VisitorRewardsFindResultDtoTest {

    @Test
    void 사용_가능한_리워드는_updatedAt이_null이다() {
        Reward reward = new Reward(
                LocalDateTime.now(),
                null,
                1L,
                "아메리카노",
                false,
                CustomerFixture.REGISTER_CUSTOMER_GITCHAN_SAVED,
                CafeFixture.GITCHAN_CAFE
        );

        assertAll(
                () -> assertDoesNotThrow(() -> VisitorRewardsFindResultDto.from(reward)),
                () -> assertThat(VisitorRewardsFindResultDto.from(reward).getUpdatedAt()).isNull()
        );
    }
}
