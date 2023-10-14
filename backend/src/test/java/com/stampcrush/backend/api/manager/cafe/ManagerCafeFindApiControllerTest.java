package com.stampcrush.backend.api.manager.cafe;

import com.stampcrush.backend.api.ControllerSliceTest;
import com.stampcrush.backend.application.manager.cafe.ManagerCafeFindService;
import com.stampcrush.backend.application.manager.cafe.dto.CafeFindResultDto;
import com.stampcrush.backend.config.WebMvcConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = ManagerCafeFindApiController.class,
        excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebMvcConfig.class))
class ManagerCafeFindApiControllerTest extends ControllerSliceTest {

    @MockBean
    private ManagerCafeFindService managerCafeFindService;

    @Test
    void 사장이_자신의_카페를_조회한다() throws Exception {
        // given
        CafeFindResultDto resultDto =
                new CafeFindResultDto(1L, "깃짱카페",
                        LocalTime.NOON,
                        LocalTime.MIDNIGHT,
                        "01012345678",
                        "cafeImageUrl",
                        "introduction",
                        "roadAddress",
                        "businessRegistrationNumber",
                        "introduction");

        given(managerCafeFindService.findCafesByOwner(any()))
                .willReturn(List.of(resultDto));

        // when, then
        mockMvc.perform(
                        get("/api/admin/cafes")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("cafes[0].id").value(resultDto.getId()))
                .andExpect(jsonPath("cafes[0].name").value(resultDto.getName()))
                .andExpect(jsonPath("cafes[0].openTime").value(resultDto.getOpenTime().format(DateTimeFormatter.ofPattern("HH:mm:ss"))))
                .andExpect(jsonPath("cafes[0].closeTime").value(resultDto.getCloseTime().format(DateTimeFormatter.ofPattern("HH:mm:ss"))))
                .andExpect(jsonPath("cafes[0].telephoneNumber").value(resultDto.getTelephoneNumber()))
                .andExpect(jsonPath("cafes[0].cafeImageUrl").value(resultDto.getCafeImageUrl()))
                .andExpect(jsonPath("cafes[0].roadAddress").value(resultDto.getRoadAddress()))
                .andExpect(jsonPath("cafes[0].detailAddress").value(resultDto.getDetailAddress()))
                .andExpect(jsonPath("cafes[0].businessRegistrationNumber").value(resultDto.getBusinessRegistrationNumber()))
                .andExpect(jsonPath("cafes[0].introduction").value(resultDto.getIntroduction()));
    }
}
