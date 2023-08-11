package com.stampcrush.backend.api.visitor.reward;

import com.stampcrush.backend.application.visitor.reward.VisitorRewardsFindService;
import com.stampcrush.backend.application.visitor.reward.dto.VisitorRewardsFindResultDto;
import com.stampcrush.backend.config.WebMvcConfig;
import com.stampcrush.backend.fixture.RewardFixture;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

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
        when(visitorRewardsFindService.findRewards(null, false))
                .thenReturn(
                        List.of(
                                VisitorRewardsFindResultDto.from(RewardFixture.REWARD_USED_FALSE)
                        )
                );

        mockMvc.perform(
                        get("/api/rewards")
                                .param("used", "false")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("rewards").isNotEmpty());
    }

    @Test
    void 고객모드에서_사용_완료한_리워드를_조회한다() throws Exception {
        when(visitorRewardsFindService.findRewards(null, true))
                .thenReturn(
                        List.of(
                                VisitorRewardsFindResultDto.from(RewardFixture.REWARD_USED_TRUE)
                        )
                );

        mockMvc.perform(
                        get("/api/rewards")
                                .param("used", "true")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("rewards").isNotEmpty());
    }
}
