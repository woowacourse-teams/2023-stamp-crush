package com.stampcrush.backend.api.visitor.cafe;

import com.stampcrush.backend.api.ControllerSliceTest;
import com.stampcrush.backend.application.visitor.cafe.VisitorCafeFindService;
import com.stampcrush.backend.application.visitor.cafe.dto.CafeInfoFindByCustomerResultDto;
import com.stampcrush.backend.config.WebMvcConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = VisitorCafeFindApiController.class,
        excludeFilters =
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebMvcConfig.class))
class VisitorCafeFindApiControllerTest extends ControllerSliceTest {

    @MockBean
    private VisitorCafeFindService visitorCafeFindService;

    @Test
    void 고객이_카페_정보를_요청한다() throws Exception {
        // given
        CafeInfoFindByCustomerResultDto resultDto = new CafeInfoFindByCustomerResultDto(1L, "깃짱카페",
                "introduction",
                LocalTime.NOON,
                LocalTime.MIDNIGHT,
                "01012345678",
                "cafeImageUrl",
                "roadAddress",
                "roadAddress"
        );

        when(visitorCafeFindService.findCafeById(anyLong()))
                .thenReturn(resultDto);

        // when, then
        mockMvc.perform(get("/api/cafes/" + 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("cafe.id").value(resultDto.getId()))
                .andExpect(jsonPath("cafe.name").value(resultDto.getName()))
                .andExpect(jsonPath("cafe.openTime").value(resultDto.getOpenTime().format(DateTimeFormatter.ofPattern("HH:mm"))))
                .andExpect(jsonPath("cafe.closeTime").value(resultDto.getCloseTime().format(DateTimeFormatter.ofPattern("HH:mm"))))
                .andExpect(jsonPath("cafe.telephoneNumber").value(resultDto.getTelephoneNumber()))
                .andExpect(jsonPath("cafe.cafeImageUrl").value(resultDto.getCafeImageUrl()))
                .andExpect(jsonPath("cafe.roadAddress").value(resultDto.getRoadAddress()))
                .andExpect(jsonPath("cafe.detailAddress").value(resultDto.getDetailAddress()))
                .andExpect(jsonPath("cafe.introduction").value(resultDto.getIntroduction()));
        ;
    }
}
