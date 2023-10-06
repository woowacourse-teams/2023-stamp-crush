package com.stampcrush.backend.api.manager.customer;

import com.stampcrush.backend.api.ControllerSliceTest;
import com.stampcrush.backend.application.manager.coupon.dto.CafeCustomerFindResultDto;
import com.stampcrush.backend.application.manager.customer.ManagerCustomerFindService;
import com.stampcrush.backend.config.WebMvcConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = ManagerCustomerFindService.class,
        excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebMvcConfig.class))
class ManagerCustomerFindApiControllerTest extends ControllerSliceTest {

    private static String API_PREFIX = "/api/admin";

    @MockBean
    private ManagerCustomerFindService managerCustomerFindService;

    @Test
    void 카페에_방문한_고객들의_정보를_조회한다() throws Exception {
        // given, when
        CafeCustomerFindResultDto customerInfo1 = new CafeCustomerFindResultDto(1L, "name1", 5, 0, 3, LocalDateTime.now(), false, 10, null);
        CafeCustomerFindResultDto customerInfo2 = new CafeCustomerFindResultDto(2L, "name2", 6, 0, 6, LocalDateTime.now(), true, 10, null);
        given(managerCustomerFindService.findCouponsByCafe(anyLong(), anyLong()))
                .willReturn(List.of(customerInfo1, customerInfo2));

        // then
        mockMvc.perform(get(API_PREFIX + "/cafes/{cafeId}/customers", 1L))
                .andExpect(status().isOk());
    }

    @Test
    void 카페에_방문한_고객들의_타입_별로_정보를_조회한다() throws Exception {
        // given, when
        CafeCustomerFindResultDto customerInfo1 = new CafeCustomerFindResultDto(1L, "name1", 5, 0, 3, LocalDateTime.now(), false, 10, null);
        CafeCustomerFindResultDto customerInfo2 = new CafeCustomerFindResultDto(2L, "name2", 6, 0, 6, LocalDateTime.now(), false, 10, null);
        given(managerCustomerFindService.findCouponsByCafeAndCustomerType(anyLong(), anyLong(), anyString()))
                .willReturn(List.of(customerInfo1, customerInfo2));

        // then
        mockMvc.perform(get(API_PREFIX + "/cafes/{cafeId}/customers", 1L)
                        .param("status", "temporary"))
                .andExpect(status().isOk());
    }

    @Test
    void 특정_카페의_적립중인_쿠폰이_있는_고객을_조회한다() throws Exception {
        // when, then
        mockMvc.perform(get(API_PREFIX + "/customers/{customerId}/coupons", 1L)
                        .param("cafe-id", String.valueOf(1L))
                        .param("active", "true"))
                .andExpect(status().isOk());
    }
}
