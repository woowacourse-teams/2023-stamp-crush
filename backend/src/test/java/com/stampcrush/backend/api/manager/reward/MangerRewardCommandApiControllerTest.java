package com.stampcrush.backend.api.manager.reward;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stampcrush.backend.api.ControllerSliceTest;
import com.stampcrush.backend.api.manager.reward.request.RewardUsedUpdateRequest;
import com.stampcrush.backend.application.manager.reward.ManagerRewardCommandService;
import com.stampcrush.backend.application.manager.reward.dto.RewardUsedUpdateDto;
import com.stampcrush.backend.config.WebMvcConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = ManagerRewardCommandApiController.class,
        excludeFilters =
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebMvcConfig.class))
class MangerRewardCommandApiControllerTest extends ControllerSliceTest {

    @Test
    void 리워드를_사용한다() throws Exception {
        // given
        RewardUsedUpdateRequest request = new RewardUsedUpdateRequest(1L, true);
        doNothing()
                .when(managerRewardCommandService)
                .useReward(any(), any(RewardUsedUpdateDto.class));

        // when, then
        mockMvc.perform(
                        patch("/api/admin/customers/1/rewards/1")
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void 리워드_사용시_used_가_false_이면_상태코드가_BAD_REQUEST_이다() throws Exception {
        // given
        RewardUsedUpdateRequest request = new RewardUsedUpdateRequest(1L, false);

        // when, then
        mockMvc.perform(
                        patch("/api/admin/customers/1/rewards/1")
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void 리워드_사용시_cafeId_가_양수가_아니면_상태코드가_BAD_REQUEST_이다() throws Exception {
        // given
        RewardUsedUpdateRequest request = new RewardUsedUpdateRequest(-1L, true);

        // when, then
        mockMvc.perform(
                        patch("/api/admin/customers/1/rewards/1")
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}
