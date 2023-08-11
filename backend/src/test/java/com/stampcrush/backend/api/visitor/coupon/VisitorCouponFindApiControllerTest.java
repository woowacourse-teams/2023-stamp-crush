package com.stampcrush.backend.api.visitor.coupon;

import com.stampcrush.backend.api.ControllerTest;
import com.stampcrush.backend.application.visitor.coupon.VisitorCouponFindService;
import com.stampcrush.backend.application.visitor.coupon.dto.CustomerCouponFindResultDto;
import com.stampcrush.backend.entity.user.RegisterCustomer;
import com.stampcrush.backend.fixture.CafeFixture;
import com.stampcrush.backend.fixture.CouponFixture;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static com.stampcrush.backend.fixture.CustomerFixture.REGISTER_CUSTOMER_GITCHAN;
import static com.stampcrush.backend.fixture.CustomerFixture.REGISTER_CUSTOMER_GITCHAN_SAVED;
import static com.stampcrush.backend.helper.AuthHelper.CustomerAuthorization;
import static com.stampcrush.backend.helper.AuthHelper.createCustomerAuthorization;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(VisitorCouponFindApiController.class)
class VisitorCouponFindApiControllerTest extends ControllerTest {

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
        CustomerAuthorization customerAuthorization = createCustomerAuthorization(REGISTER_CUSTOMER_GITCHAN);

        when(registerCustomerRepository.findByLoginId(customerAuthorization.loginId()))
                .thenReturn(Optional.empty());

        mockMvc.perform(
                        get("/api/coupons")
                                .contentType(APPLICATION_JSON)
                                .header(AUTHORIZATION, customerAuthorization.basicAuthHeader())
                )
                .andExpect(status().isUnauthorized());
    }

    @Test
    void 고객의_쿠폰_조회_요청_시_인증이_되면_200_상태코드와_응답을_반환한다() throws Exception {
        RegisterCustomer customer = REGISTER_CUSTOMER_GITCHAN_SAVED;

        CustomerAuthorization customerAuthorization = createCustomerAuthorization(customer);

        when(registerCustomerRepository.findByLoginId(customerAuthorization.loginId()))
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
                                .header(AUTHORIZATION, customerAuthorization.basicAuthHeader())
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("coupons").isNotEmpty());
    }
}
