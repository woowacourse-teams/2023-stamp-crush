package com.stampcrush.backend.api.manager.sample;

import com.stampcrush.backend.api.ControllerSliceTest;
import com.stampcrush.backend.application.manager.sample.dto.SampleCouponsFindResultDto;
import com.stampcrush.backend.fixture.SampleCouponFixture;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.stampcrush.backend.fixture.SampleCouponFixture.SAMPLE_BACK_IMAGE_SAVED;
import static com.stampcrush.backend.fixture.SampleCouponFixture.SAMPLE_FRONT_IMAGE_SAVED;
import static com.stampcrush.backend.fixture.SampleCouponFixture.SAMPLE_STAMP_IMAGE_SAVED;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ManagerSampleCouponFindApiControllerTest extends ControllerSliceTest {

    @Test
    void 샘플_쿠폰_조회_요청한다() throws Exception {
        int maxStampCount = 8;

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
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("sampleFrontImages").isNotEmpty())
                .andExpect(jsonPath("sampleBackImages").isNotEmpty())
                .andExpect(jsonPath("sampleStampImages").isNotEmpty());
    }
}
