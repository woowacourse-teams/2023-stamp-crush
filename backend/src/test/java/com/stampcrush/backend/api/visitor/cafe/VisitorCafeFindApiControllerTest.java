package com.stampcrush.backend.api.visitor.cafe;

import com.stampcrush.backend.api.ControllerSliceTest;
import com.stampcrush.backend.application.visitor.cafe.VisitorCafeFindService;
import com.stampcrush.backend.application.visitor.cafe.dto.CafeInfoFindByCustomerResultDto;
import com.stampcrush.backend.entity.user.RegisterCustomer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.time.LocalTime;
import java.util.Base64;
import java.util.Optional;

import static com.stampcrush.backend.fixture.CustomerFixture.REGISTER_CUSTOMER_GITCHAN;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(VisitorCafeFindApiController.class)
class VisitorCafeFindApiControllerTest extends ControllerSliceTest {

    private static final Long CAFE_ID = 1L;

    @MockBean
    private VisitorCafeFindService visitorCafeFindService;

    private RegisterCustomer customer;
    private String basicAuthHeader;

    @BeforeEach
    void setUp() {
        customer = REGISTER_CUSTOMER_GITCHAN;

        String username = customer.getLoginId();
        String password = customer.getEncryptedPassword();
        basicAuthHeader = "Basic " + Base64.getEncoder().encodeToString((username + ":" + password).getBytes());
    }

    @Test
    void 카페_조회_요청_시_인증_헤더_정보가_없으면_401코드_반환() throws Exception {
        mockMvc.perform(get("/api/cafes/" + CAFE_ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void 카페_조회_요청_시_고객_인증이_안되면_401코드_반환() throws Exception {
        // given
        when(registerCustomerRepository.findByLoginId(customer.getLoginId())).thenReturn(Optional.empty());

        // when, then
        mockMvc.perform(get("/api/cafes/" + CAFE_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, basicAuthHeader))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void 카페_조회_요청_시_고객_인증되면_200코드_반환() throws Exception {
        // given
        when(registerCustomerRepository.findByLoginId(customer.getLoginId())).thenReturn(Optional.of(customer));
        when(visitorCafeFindService.findCafeById(CAFE_ID)).thenReturn(new CafeInfoFindByCustomerResultDto(CAFE_ID, "cafe", "안녕하세요", LocalTime.MIDNIGHT, LocalTime.NOON, "01012345678", "image", "address", "detail"));

        // when, then
        mockMvc.perform(get("/api/cafes/" + CAFE_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, basicAuthHeader))
                .andExpect(status().isOk());
    }

    @Test
    void 카페_조회_요청_시_비밀번호가_틀리면_401코드_반환() throws Exception {
        // given
        when(registerCustomerRepository.findByLoginId(customer.getLoginId())).thenReturn(Optional.of(new RegisterCustomer("깃짱", "01012345678", "gitchan", "wrong")));

        // when, then
        mockMvc.perform(get("/api/cafes/" + 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, basicAuthHeader))
                .andExpect(status().isUnauthorized());
    }
}
