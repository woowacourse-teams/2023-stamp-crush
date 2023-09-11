package com.stampcrush.backend.api.manager.cafe;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stampcrush.backend.api.ControllerSliceTest;
import com.stampcrush.backend.api.manager.cafe.request.CafeCouponSettingUpdateRequest;
import com.stampcrush.backend.application.manager.cafe.ManagerCafeCouponSettingCommandService;
import com.stampcrush.backend.config.WebMvcConfig;
import com.stampcrush.backend.entity.user.Owner;
import com.stampcrush.backend.fixture.OwnerFixture;
import com.stampcrush.backend.helper.AuthHelper.OwnerAuthorization;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

import java.util.List;
import java.util.Optional;

import static com.stampcrush.backend.helper.AuthHelper.createOwnerAuthorization;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = ManagerCafeCouponSettingCommandApiController.class,
     excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebMvcConfig.class))
class ManagerCafeCouponSettingCommandApiControllerTest extends ControllerSliceTest {

    private static final CafeCouponSettingUpdateRequest CAFE_COUPON_SETTING_UPDATE_REQUEST = new CafeCouponSettingUpdateRequest(
            "frontImageUrl",
            "backImageUrl",
            "stampImageUrl",
            List.of(new CafeCouponSettingUpdateRequest.CouponStampCoordinateRequest(1, 1, 1)),
            "reward",
            6
    );

    @MockBean
    private ManagerCafeCouponSettingCommandService managerCafeCouponSettingCommandService;

    @Test
    void 카페_쿠폰_정책_변경_시_인증이_되면_204_상태코드와_응답을_반환한다() throws Exception {
        Owner owner = OwnerFixture.GITCHAN;

        OwnerAuthorization ownerAuthorization = createOwnerAuthorization(owner);

        when(ownerRepository.findByLoginId(ownerAuthorization.getOwner().getLoginId()))
                .thenReturn(Optional.of(owner));

        String requestBody = formatRequestBody(CAFE_COUPON_SETTING_UPDATE_REQUEST);

        mockMvc.perform(
                        post("/api/admin/coupon-setting?cafe-id=1")
                                .contentType(APPLICATION_JSON)
                                .content(requestBody)
                                .header(AUTHORIZATION, ownerAuthorization.getBasicAuthHeader())
                )
                .andExpect(status().isNoContent());
    }

    private String formatRequestBody(CafeCouponSettingUpdateRequest request) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(request);
    }
}
