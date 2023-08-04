package com.stampcrush.backend.api.visitor.cafe;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.Schema;
import com.stampcrush.backend.api.ControllerTest;
import com.stampcrush.backend.application.visitor.cafe.dto.CafeInfoFindByCustomerResultDto;
import com.stampcrush.backend.entity.user.RegisterCustomer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;

import java.time.LocalTime;
import java.util.Base64;
import java.util.Optional;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static com.stampcrush.backend.fixture.CustomerFixture.REGISTER_CUSTOMER_GITCHAN;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class VisitorCafeFindApiControllerTest extends ControllerTest {

    private static final Long CAFE_ID = 1L;

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
        when(customerRepository.findByLoginId(customer.getLoginId())).thenReturn(Optional.empty());

        // when, then
        mockMvc.perform(get("/api/cafes/" + CAFE_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, basicAuthHeader))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void 카페_조회_요청_시_고객_인증되면_200코드_반환() throws Exception {
        // given
        when(customerRepository.findByLoginId(customer.getLoginId())).thenReturn(Optional.of(customer));
        when(visitorCafeFindService.findCafeById(CAFE_ID)).thenReturn(new CafeInfoFindByCustomerResultDto(CAFE_ID, "우아한카페", "안녕하세요", LocalTime.MIDNIGHT, LocalTime.NOON, "01012345678", "http://imge.co", "서울시 송파구", "루터회관"));

        // when, then
        mockMvc.perform(RestDocumentationRequestBuilders.get("/api/cafes/{cafeId}", CAFE_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, basicAuthHeader))
                .andDo(document("visitor/cafe",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                resource(
                                        ResourceSnippetParameters.builder()
                                                .tag("고객 모드")
                                                .description("카페 정보 조회")
                                                .requestHeaders(headerWithName("Authorization").description("임시(Basic)"))
                                                .responseFields(
                                                        fieldWithPath("cafe.id").description("카페 ID"),
                                                        fieldWithPath("cafe.name").description("카페 이름"),
                                                        fieldWithPath("cafe.introduction").description("카페 소개글"),
                                                        fieldWithPath("cafe.openTime").description("카페 오픈 시간"),
                                                        fieldWithPath("cafe.closeTime").description("카페 닫는 시간"),
                                                        fieldWithPath("cafe.telephoneNumber").description("카페 전화번호"),
                                                        fieldWithPath("cafe.cafeImageUrl").description("카페 이미지 URL"),
                                                        fieldWithPath("cafe.roadAddress").description("카페 도로명주소"),
                                                        fieldWithPath("cafe.detailAddress").description("카페 상세 주소")
                                                )
                                                .responseSchema(Schema.schema("CafeInfoFindByCustomerResponse"))
                                                .build()
                                )
                        )
                )
                .andExpect(status().isOk());
    }

    @Test
    void 카페_조회_요청_시_비밀번호가_틀리면_401코드_반환() throws Exception {
        // given
        when(customerRepository.findByLoginId(customer.getLoginId())).thenReturn(Optional.of(new RegisterCustomer("깃짱", "01012345678", "gitchan", "wrong")));

        // when, then
        mockMvc.perform(get("/api/cafes/" + 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, basicAuthHeader))
                .andExpect(status().isUnauthorized());
    }
}
