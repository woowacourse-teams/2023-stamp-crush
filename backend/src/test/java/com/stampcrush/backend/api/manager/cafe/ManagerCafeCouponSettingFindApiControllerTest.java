package com.stampcrush.backend.api.manager.cafe;

import com.stampcrush.backend.api.ControllerSliceTest;
import com.stampcrush.backend.application.manager.cafe.ManagerCafeCouponSettingFindService;
import com.stampcrush.backend.application.manager.cafe.dto.CafeCouponCoordinateFindResultDto;
import com.stampcrush.backend.application.manager.cafe.dto.CafeCouponSettingFindResultDto;
import com.stampcrush.backend.config.WebMvcConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = ManagerCafeCouponSettingFindApiController.class,
        excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebMvcConfig.class))
public class ManagerCafeCouponSettingFindApiControllerTest extends ControllerSliceTest {

    @MockBean
    private ManagerCafeCouponSettingFindService cafeCouponSettingFindService;

    @Test
    void 카페의_카페_쿠폰_세팅을_조회한다() throws Exception {
        // given
        CafeCouponSettingFindResultDto resultDto
                = new CafeCouponSettingFindResultDto(
                "frontImageUrl",
                "backImageUrl",
                "stampImageUrl",
                List.of(
                        new CafeCouponCoordinateFindResultDto(1, 1, 1),
                        new CafeCouponCoordinateFindResultDto(2, 2, 2),
                        new CafeCouponCoordinateFindResultDto(3, 3, 3),
                        new CafeCouponCoordinateFindResultDto(4, 4, 4),
                        new CafeCouponCoordinateFindResultDto(5, 5, 5)
                )
        );

        given(cafeCouponSettingFindService.findCafeCouponSetting(1L))
                .willReturn(resultDto);

        // when, then
        mockMvc.perform(get("/api/admin/coupon-setting")
                        .param("cafe-id", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("frontImageUrl").value(resultDto.getFrontImageUrl()))
                .andExpect(jsonPath("backImageUrl").value(resultDto.getBackImageUrl()))
                .andExpect(jsonPath("stampImageUrl").value(resultDto.getStampImageUrl()))
                .andExpect(jsonPath("coordinates").isArray())
                .andExpect(jsonPath("coordinates", hasSize(resultDto.getCoordinates().size())))
                .andExpect(jsonPath("coordinates[0].order").value(resultDto.getCoordinates().get(0).getOrder()))
                .andExpect(jsonPath("coordinates[0].xCoordinate").value(resultDto.getCoordinates().get(0).getXCoordinate()))
                .andExpect(jsonPath("coordinates[0].yCoordinate").value(resultDto.getCoordinates().get(0).getYCoordinate()))
                .andExpect(jsonPath("coordinates[1].order").value(resultDto.getCoordinates().get(1).getOrder()))
                .andExpect(jsonPath("coordinates[1].xCoordinate").value(resultDto.getCoordinates().get(1).getXCoordinate()))
                .andExpect(jsonPath("coordinates[1].yCoordinate").value(resultDto.getCoordinates().get(1).getYCoordinate()))
                .andExpect(jsonPath("coordinates[2].order").value(resultDto.getCoordinates().get(2).getOrder()))
                .andExpect(jsonPath("coordinates[2].xCoordinate").value(resultDto.getCoordinates().get(2).getXCoordinate()))
                .andExpect(jsonPath("coordinates[2].yCoordinate").value(resultDto.getCoordinates().get(2).getYCoordinate()))
                .andExpect(jsonPath("coordinates[3].order").value(resultDto.getCoordinates().get(3).getOrder()))
                .andExpect(jsonPath("coordinates[3].xCoordinate").value(resultDto.getCoordinates().get(3).getXCoordinate()))
                .andExpect(jsonPath("coordinates[3].yCoordinate").value(resultDto.getCoordinates().get(3).getYCoordinate()))
                .andExpect(jsonPath("coordinates[4].order").value(resultDto.getCoordinates().get(4).getOrder()))
                .andExpect(jsonPath("coordinates[4].xCoordinate").value(resultDto.getCoordinates().get(4).getXCoordinate()))
                .andExpect(jsonPath("coordinates[4].yCoordinate").value(resultDto.getCoordinates().get(4).getYCoordinate()));
    }
}
