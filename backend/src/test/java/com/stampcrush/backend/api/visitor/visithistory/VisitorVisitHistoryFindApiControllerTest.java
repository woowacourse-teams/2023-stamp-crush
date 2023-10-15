package com.stampcrush.backend.api.visitor.visithistory;

import com.stampcrush.backend.api.ControllerSliceTest;
import com.stampcrush.backend.application.visitor.visithistory.dto.CustomerStampHistoryFindResultDto;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

class VisitorVisitHistoryFindApiControllerTest extends ControllerSliceTest {

    @Test
    void 고객의_스탬프_적립_내역을_조회한다() throws Exception {
        // given
        CustomerStampHistoryFindResultDto expected1 = new CustomerStampHistoryFindResultDto(1L, "cafe1", 3, null);
        CustomerStampHistoryFindResultDto expected2 = new CustomerStampHistoryFindResultDto(2L, "cafe2", 5, null);

        // when
        given(visitorVisitHistoryFindService.findStampHistoriesByCustomer(anyLong()))
                .willReturn(List.of(expected1, expected2));

        // then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/stamp-histories"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
