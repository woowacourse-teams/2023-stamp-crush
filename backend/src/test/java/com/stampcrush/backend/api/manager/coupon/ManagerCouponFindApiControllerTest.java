package com.stampcrush.backend.api.manager.coupon;

import com.stampcrush.backend.api.ControllerTest;
import com.stampcrush.backend.application.manager.coupon.ManagerCouponFindService;
import com.stampcrush.backend.application.manager.coupon.dto.CafeCustomerFindResultDto;
import com.stampcrush.backend.config.WebMvcConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = ManagerCouponFindApiController.class,
        excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebMvcConfig.class))
public class ManagerCouponFindApiControllerTest extends ControllerTest {

    public static String API_PREFIX = "/api/admin";

    @MockBean
    private ManagerCouponFindService managerCouponFindService;

    @Test
    void 카페에_방문한_고객들의_정보를_조회한다() throws Exception {
        // given, when
        CafeCustomerFindResultDto customerInfo1 = new CafeCustomerFindResultDto(1L, "name1", 5, 0, 3, LocalDateTime.now(), false, 10);
        CafeCustomerFindResultDto customerInfo2 = new CafeCustomerFindResultDto(2L, "name2", 6, 0, 6, LocalDateTime.now(), true, 10);
        given(managerCouponFindService.findCouponsByCafe(anyLong()))
                .willReturn(List.of(customerInfo1, customerInfo2));

        // then
        mockMvc.perform(get(API_PREFIX + "/cafes/{cafeId}/customers", 1L))
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
