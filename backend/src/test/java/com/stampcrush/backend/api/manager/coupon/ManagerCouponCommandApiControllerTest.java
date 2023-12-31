package com.stampcrush.backend.api.manager.coupon;

import com.stampcrush.backend.api.ControllerSliceTest;
import com.stampcrush.backend.api.manager.coupon.request.CouponCreateRequest;
import com.stampcrush.backend.api.manager.coupon.request.StampCreateRequest;
import com.stampcrush.backend.api.manager.coupon.response.CouponCreateResponse;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ManagerCouponCommandApiControllerTest extends ControllerSliceTest {

    public static String API_PREFIX = "/api/admin";

    @Test
    void 쿠폰을_신규_발급한다() throws Exception {
        // given
        given(managerCouponCommandService.createCoupon(any(), any(), any()))
                .willReturn(1L);

        CouponCreateRequest couponCreateRequest = new CouponCreateRequest(1L);
        String request = objectMapper.writeValueAsString(couponCreateRequest);

        // when
        MvcResult mvcResult = mockMvc.perform(
                        post("/api/admin/customers/{customerId}/coupons", 1L)
                                .content(request)
                                .contentType(APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        // then
        String responseBody = mvcResult.getResponse().getContentAsString();
        CouponCreateResponse couponCreateResponse = objectMapper.readValue(responseBody, CouponCreateResponse.class);
        assertThat(couponCreateResponse.getCouponId()).isEqualTo(1);
    }

    @Test
    void 카페_id가_음수면_예외발생() throws Exception {
        // given
        CouponCreateRequest couponCreateRequest = new CouponCreateRequest(-1L);
        String request = objectMapper.writeValueAsString(couponCreateRequest);

        // when, then
        mockMvc.perform(post("/api/admin/customers/{customerId}/coupons", 1L)
                .content(request)
                .contentType(APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
    }

    @Test
    void 스탬프를_적립한다() throws Exception {
        // given, when
        StampCreateRequest stampCreateRequest = new StampCreateRequest(4);
        String request = objectMapper.writeValueAsString(stampCreateRequest);

        // then
        mockMvc.perform(post(API_PREFIX + "/customers/{customerId}/coupons/{couponId}/stamps", 1L, 1L)
                        .content(request)
                        .contentType(APPLICATION_JSON)
                )
                .andExpect(status().isCreated());
    }

    @Test
    void 적립하려는_스탬프가_음수면_예외발생() throws Exception {
        // given, when
        StampCreateRequest stampCreateRequest = new StampCreateRequest(-1);
        String request = objectMapper.writeValueAsString(stampCreateRequest);

        // then
        mockMvc.perform(post(API_PREFIX + "/customers/{customerId}/coupons/{couponId}/stamps", 1L, 1L)
                        .content(request)
                        .contentType(APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest());
    }
}
