package com.stampcrush.backend.api.visitor.coupon;

import com.stampcrush.backend.api.ControllerSliceTest;
import com.stampcrush.backend.application.visitor.coupon.VisitorCouponFindService;
import com.stampcrush.backend.application.visitor.coupon.dto.CustomerCouponFindResultDto;
import com.stampcrush.backend.entity.user.Customer;
import com.stampcrush.backend.fixture.CafeFixture;
import com.stampcrush.backend.fixture.CouponFixture;
import com.stampcrush.backend.helper.BearerAuthHelper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static com.stampcrush.backend.fixture.CustomerFixture.REGISTER_CUSTOMER_GITCHAN_SAVED;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(VisitorCouponFindApiController.class)
class VisitorCouponFindApiControllerTest extends ControllerSliceTest {

    @MockBean
    private VisitorCouponFindService visitorCouponFindService;

    @Test
    void 고객의_쿠폰_조회_요청_시_인증_헤더가_없으면_401_상태코드를_반환한다() throws Exception {
        mockMvc.perform(
                        get("/api/coupons")
                                .contentType(APPLICATION_JSON)
                )
                .andExpect(status().isUnauthorized());
    }

    @Test
    void 고객의_쿠폰_조회_요청_시_인증이_안되면_401_상태코드를_반환한다() throws Exception {
        Customer customer = REGISTER_CUSTOMER_GITCHAN_SAVED;

        when(customerRepository.findByLoginId(customer.getLoginId()))
                .thenReturn(Optional.empty());

        mockMvc.perform(
                        get("/api/coupons")
                                .contentType(APPLICATION_JSON)
                                .header(AUTHORIZATION, "Bearer " + BearerAuthHelper.generateToken(customer.getId()))
                )
                .andExpect(status().isUnauthorized());
    }

    @Test
    void 고객의_쿠폰_조회_요청_시_인증이_되면_200_상태코드와_응답을_반환한다() throws Exception {
        Customer customer = REGISTER_CUSTOMER_GITCHAN_SAVED;

        when(customerRepository.findById(customer.getId()))
                .thenReturn(Optional.of(customer));
        when(customerRepository.findByLoginId(customer.getLoginId()))
                .thenReturn(Optional.of(customer));

        when(visitorCouponFindService.findOneCouponForOneCafe(customer.getId()))
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
                                .header(AUTHORIZATION, "Bearer " + BearerAuthHelper.generateToken(customer.getId()))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("coupons").isNotEmpty());
    }
}
