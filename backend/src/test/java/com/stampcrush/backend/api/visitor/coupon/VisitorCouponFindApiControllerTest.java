package com.stampcrush.backend.api.visitor.coupon;

import com.stampcrush.backend.api.ControllerSliceTest;
import com.stampcrush.backend.application.visitor.coupon.VisitorCouponFindService;
import com.stampcrush.backend.application.visitor.coupon.dto.CustomerCouponFindResultDto;
import com.stampcrush.backend.config.WebMvcConfig;
import com.stampcrush.backend.fixture.CafeFixture;
import com.stampcrush.backend.fixture.CouponFixture;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = VisitorCouponFindApiController.class,
        excludeFilters =
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebMvcConfig.class))
class VisitorCouponFindApiControllerTest extends ControllerSliceTest {

    @MockBean
    private VisitorCouponFindService visitorCouponFindService;

    @Test
    void 고객의_쿠폰_조회_요청_시_인증이_되면_200_상태코드와_응답을_반환한다() throws Exception {
        when(visitorCouponFindService.findOneCouponForOneCafe(any()))
                .thenReturn(
                        List.of(
                                CustomerCouponFindResultDto.of(
                                        CafeFixture.GITCHAN_CAFE,
                                        CouponFixture.GITCHAN_CAFE_COUPON,
                                        true,
                                        CouponFixture.GITCHAN_CAFE_COUPON_STAMP_COORDINATE
                                )
                        )
                );

        mockMvc.perform(
                        get("/api/coupons")
                                .contentType(APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("coupons").isNotEmpty());
    }
}
