package com.stampcrush.backend.api.visitor.reward;

import com.stampcrush.backend.application.visitor.reward.VisitorRewardsFindService;
import com.stampcrush.backend.application.visitor.reward.dto.VisitorRewardsFindResultDto;
import com.stampcrush.backend.config.WebMvcConfig;
import com.stampcrush.backend.entity.reward.Reward;
import com.stampcrush.backend.fixture.RewardFixture;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(
        value = VisitorRewardsFindController.class,
        excludeFilters =
        @ComponentScan.Filter(
                type = FilterType.ASSIGNABLE_TYPE,
                classes = WebMvcConfig.class
        )
)
class VisitorRewardsFindControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VisitorRewardsFindService visitorRewardsFindService;

    @Test
    void 고객모드에서_사용_가능한_리워드를_조회한다() throws Exception {
        // given, when
        Reward reward = RewardFixture.REWARD_USED_FALSE;

        when(visitorRewardsFindService.findRewards(null, false))
                .thenReturn(
                        List.of(
                                VisitorRewardsFindResultDto.from(reward)
                        )
                );

        // then
        mockMvc.perform(
                        get("/api/rewards")
                                .param("used", "false")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("rewards").isNotEmpty())
                .andExpect(jsonPath("rewards[0].id").value(reward.getId()))
                .andExpect(jsonPath("rewards[0].rewardName").value(reward.getName()))
                .andExpect(jsonPath("rewards[0].cafeName").value(reward.getCafe().getName()))
                .andExpect(jsonPath("rewards[0].createdAt").value(String.valueOf(LocalDate.from(reward.getCreatedAt()))))
                .andExpect(jsonPath("rewards[0].usedAt").value(String.valueOf(LocalDate.from(reward.getUpdatedAt()))));
    }

    @Test
    void 고객모드에서_사용_완료한_리워드를_조회한다() throws Exception {
        // given, when
        Reward reward = RewardFixture.REWARD_USED_TRUE;

        when(visitorRewardsFindService.findRewards(null, true))
                .thenReturn(
                        List.of(
                                VisitorRewardsFindResultDto.from(reward)
                        )
                );

        // then
        mockMvc.perform(
                        get("/api/rewards")
                                .param("used", "true")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("rewards").isNotEmpty())
                .andExpect(jsonPath("rewards[0].id").value(reward.getId()))
                .andExpect(jsonPath("rewards[0].rewardName").value(reward.getName()))
                .andExpect(jsonPath("rewards[0].cafeName").value(reward.getCafe().getName()))
                .andExpect(jsonPath("rewards[0].createdAt").value(String.valueOf(LocalDate.from(reward.getCreatedAt()))))
                .andExpect(jsonPath("rewards[0].usedAt").value(String.valueOf(LocalDate.from(reward.getUpdatedAt()))));
        ;
    }
}
