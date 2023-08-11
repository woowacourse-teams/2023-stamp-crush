package com.stampcrush.backend.api.manager.sample;

import com.stampcrush.backend.api.ControllerSliceTest;
import com.stampcrush.backend.application.manager.sample.ManagerSampleCouponFindService;
import com.stampcrush.backend.application.manager.sample.dto.SampleCouponsFindResultDto;
import com.stampcrush.backend.entity.user.Owner;
import com.stampcrush.backend.fixture.OwnerFixture;
import com.stampcrush.backend.fixture.SampleCouponFixture;
import com.stampcrush.backend.helper.AuthHelper;
import com.stampcrush.backend.helper.AuthHelper.OwnerAuthorization;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static com.stampcrush.backend.fixture.SampleCouponFixture.*;
import static com.stampcrush.backend.helper.AuthHelper.createOwnerAuthorization;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ManagerSampleCouponFindApiController.class)
class ManagerSampleCouponFindApiControllerTest extends ControllerSliceTest {

    @MockBean
    private ManagerSampleCouponFindService managerSampleCouponFindService;

    @Test
    void 샘플_쿠폰_조회_요청_시_인증_헤더_정보가_없으면_401_상태코드를_반환한다() throws Exception {
        mockMvc.perform(
                        get("/api/admin/coupon-samples")
                                .contentType(APPLICATION_JSON)
                )
                .andExpect(status().isUnauthorized());
    }

    @Test
    void 샘플_쿠폰_조회_요청_시_인증이_안되면_401_상태코드를_반환한다() throws Exception {
        OwnerAuthorization ownerAuthorization = AuthHelper.createOwnerAuthorization(OwnerFixture.GITCHAN);

        when(ownerRepository.findByLoginId(ownerAuthorization.getOwner().getLoginId()))
                .thenReturn(Optional.empty());

        mockMvc.perform(
                        get("/api/admin/coupon-samples")
                                .contentType(APPLICATION_JSON)
                                .header(AUTHORIZATION, ownerAuthorization.getBasicAuthHeader())
                )
                .andExpect(status().isUnauthorized());
    }

    @Test
    void 샘플_쿠폰_조회_요청_시_인증이_되면_200_상태코드와_응답을_반환한다() throws Exception {
        Owner owner = OwnerFixture.GITCHAN;

        OwnerAuthorization ownerAuthorization = createOwnerAuthorization(owner);
        int maxStampCount = 8;

        when(ownerRepository.findByLoginId(ownerAuthorization.getOwner().getLoginId()))
                .thenReturn(Optional.of(owner));

        when(managerSampleCouponFindService.findSampleCouponsByMaxStampCount(maxStampCount))
                .thenReturn(
                        SampleCouponsFindResultDto.of(
                                List.of(SAMPLE_FRONT_IMAGE_SAVED),
                                List.of(SAMPLE_BACK_IMAGE_SAVED),
                                SampleCouponFixture.SAMPLE_COORDINATES_SIZE_EIGHT,
                                List.of(SAMPLE_STAMP_IMAGE_SAVED)
                        )
                );

        mockMvc.perform(
                        get("/api/admin/coupon-samples?max-stamp-count=" + maxStampCount)
                                .contentType(APPLICATION_JSON)
                                .header(AUTHORIZATION, ownerAuthorization.getBasicAuthHeader())
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("sampleFrontImages").isNotEmpty())
                .andExpect(jsonPath("sampleBackImages").isNotEmpty())
                .andExpect(jsonPath("sampleStampImages").isNotEmpty());
    }
}
