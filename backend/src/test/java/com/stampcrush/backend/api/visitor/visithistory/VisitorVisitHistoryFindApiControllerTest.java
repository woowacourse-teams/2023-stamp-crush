package com.stampcrush.backend.api.visitor.visithistory;

import com.stampcrush.backend.application.visitor.visithistory.VisitorVisitHistoryFindService;
import com.stampcrush.backend.application.visitor.visithistory.dto.CustomerStampHistoryFindResultDto;
import com.stampcrush.backend.config.WebMvcConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

@WebMvcTest(value = VisitorVisitHistoryFindApiController.class,
        excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebMvcConfig.class))
class VisitorVisitHistoryFindApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VisitorVisitHistoryFindService visitorVisitHistoryFindService;

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
