package com.stampcrush.backend.api.manager.cafe;

import com.stampcrush.backend.api.ControllerSliceTest;
import com.stampcrush.backend.api.manager.cafe.request.CafeCouponSettingUpdateRequest;
import com.stampcrush.backend.config.WebMvcConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ManagerCafeCouponSettingCommandApiControllerTest extends ControllerSliceTest {

    @Test
    void 카페_쿠폰_정책_변경_시_204_상태코드와_응답을_반환한다() throws Exception {
        // given
        CafeCouponSettingUpdateRequest request = new CafeCouponSettingUpdateRequest(
                "frontImageUrl",
                "backImageUrl",
                "stampImageUrl",
                List.of(new CafeCouponSettingUpdateRequest.CouponStampCoordinateRequest(1, 1, 1)),
                "reward",
                6
        );

        doNothing().when(managerCafeCouponSettingCommandService).updateCafeCouponSetting(any(), any(), any());

        // when, then
        mockMvc.perform(
                        post("/api/admin/coupon-setting?cafe-id=1")
                                .contentType(APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isNoContent());
    }
}
