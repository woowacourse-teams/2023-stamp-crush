package com.stampcrush.backend.api.manager.reward;

import com.stampcrush.backend.api.ControllerTest;
import com.stampcrush.backend.application.manager.reward.ManagerRewardFindService;
import com.stampcrush.backend.application.manager.reward.dto.RewardFindDto;
import com.stampcrush.backend.application.manager.reward.dto.RewardFindResultDto;
import com.stampcrush.backend.common.KorNamingConverter;
import com.stampcrush.backend.config.WebMvcConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = ManagerRewardFindApiController.class,
        excludeFilters =
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebMvcConfig.class))
public class ManagerRewardFindApiControllerTest extends ControllerTest {

    @MockBean
    private ManagerRewardFindService managerRewardFindService;

    @Test
    void 리워드_목록을_조회한다() throws Exception {
        // given
        String ids = "$.rewards[?(@.id == '%s')]";
        String names = "$.rewards[?(@.name == '%s')]";
        List<RewardFindResultDto> expectedServiceReturn = List.of(
                new RewardFindResultDto(1L, "Americano"),
                new RewardFindResultDto(3L, "HotChocoLatte")
        );
        when(managerRewardFindService.findRewards(any(RewardFindDto.class)))
                .thenReturn(expectedServiceReturn);

        // when, then
        mockMvc.perform(
                        get("/api/admin/customers/1/rewards")
                                .param("cafe-id", "1")
                                .param("used", "false")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath(ids, "1").exists())
                .andExpect(jsonPath(ids, "3").exists())
                .andExpect(jsonPath(names, "Americano").exists())
                .andExpect(jsonPath(names, "HotChocoLatte").exists());
    }
}
