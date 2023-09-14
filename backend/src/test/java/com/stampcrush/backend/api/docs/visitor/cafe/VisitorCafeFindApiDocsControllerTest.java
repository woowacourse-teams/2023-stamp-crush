package com.stampcrush.backend.api.docs.visitor.cafe;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.Schema;
import com.stampcrush.backend.api.docs.DocsControllerTest;
import com.stampcrush.backend.application.visitor.cafe.dto.CafeInfoFindByCustomerResultDto;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;

import java.time.LocalTime;
import java.util.Optional;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class VisitorCafeFindApiDocsControllerTest extends DocsControllerTest {

    @Test
    void 카페_조회_요청_고객_모드() throws Exception {
        // given
        when(customerRepository.findById(CUSTOMER.getId())).thenReturn(Optional.of(CUSTOMER));
        when(customerRepository.findByLoginId(CUSTOMER.getLoginId())).thenReturn(Optional.of(CUSTOMER));
        when(visitorCafeFindService.findCafeById(CAFE_ID)).thenReturn(new CafeInfoFindByCustomerResultDto(CAFE_ID, "우아한카페", "안녕하세요", LocalTime.MIDNIGHT, LocalTime.NOON, "01012345678", "http://imge.co", "서울시 송파구", "루터회관"));
        when(authTokensGenerator.isValidToken(anyString())).thenReturn(true);
        when(authTokensGenerator.extractMemberId(anyString())).thenReturn(CUSTOMER.getId());
        // when, then
        mockMvc.perform(RestDocumentationRequestBuilders.get("/api/cafes/{cafeId}", CAFE_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, CUSTOMER_BEARER_HEADER))
                .andDo(document("visitor/cafe/find-cafe-info",
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
}
