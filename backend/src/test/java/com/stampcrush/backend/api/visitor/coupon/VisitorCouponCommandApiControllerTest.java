package com.stampcrush.backend.api.visitor.coupon;

import com.stampcrush.backend.api.ControllerSliceTest;
import com.stampcrush.backend.application.visitor.coupon.VisitorCouponCommandService;
import com.stampcrush.backend.config.WebMvcConfig;
import com.stampcrush.backend.exception.CouponNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = VisitorCouponCommandApiController.class,
        excludeFilters =
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebMvcConfig.class))
public class VisitorCouponCommandApiControllerTest extends ControllerSliceTest {

    @MockBean
    private VisitorCouponCommandService visitorCouponCommandService;

    @Test
    void 삭제_하려는_쿠폰_정보가_올바르지_않은_경우_상태코드가_404_이다() throws Exception {
        // given
        doThrow(CouponNotFoundException.class)
                .when(visitorCouponCommandService)
                .deleteCoupon(any(), any());

        // when, then
        mockMvc.perform(
                        delete("/api/coupons/{couponId}", 1L)
                                .contentType(APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void 쿠폰을_정상적으로_삭제하면_상태코드가_204_이다() throws Exception {
        // given
        doNothing()
                .when(visitorCouponCommandService)
                .deleteCoupon(any(), any());

        // when, then
        mockMvc.perform(
                        delete("/api/coupons/{couponId}", 1L)
                                .contentType(APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}
