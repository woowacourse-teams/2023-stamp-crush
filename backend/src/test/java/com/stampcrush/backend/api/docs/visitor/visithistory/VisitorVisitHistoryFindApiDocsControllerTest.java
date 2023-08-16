package com.stampcrush.backend.api.docs.visitor.visithistory;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.Schema;
import com.stampcrush.backend.api.docs.DocsControllerTest;
import com.stampcrush.backend.application.visitor.visithistory.dto.CustomerStampHistoryFindResultDto;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class VisitorVisitHistoryFindApiDocsControllerTest extends DocsControllerTest {

    @Test
    void 스탬프_적립내역_조회() throws Exception {
        // given
        when(customerRepository.findByLoginId(CUSTOMER.getLoginId())).thenReturn(Optional.of(CUSTOMER));

        CustomerStampHistoryFindResultDto expected1 =
                new CustomerStampHistoryFindResultDto(1L, "cafe1", 3, LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy:MM:dd")));
        CustomerStampHistoryFindResultDto expected2 =
                new CustomerStampHistoryFindResultDto(2L, "cafe2", 5, LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy:MM:dd")));

        // when
        given(visitorVisitHistoryFindService.findStampHistoriesByCustomer(CUSTOMER.getId()))
                .willReturn(List.of(expected1, expected2));

        // then
        mockMvc.perform(
                        RestDocumentationRequestBuilders.get("/api/stamp-histories")
                                .contentType(MediaType.APPLICATION_JSON)
                                .header(HttpHeaders.AUTHORIZATION, CUSTOMER_BASIC_HEADER))
                .andDo(document("visitor/visithistory/find-stamphistory",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                resource(
                                        ResourceSnippetParameters.builder()
                                                .tag("고객 모드")
                                                .description("스탬프 적립내역 조회")
                                                .requestHeaders(headerWithName("Authorization").description("임시(Basic)"))
                                                .responseFields(
                                                        fieldWithPath("stampHistory[].id").description("적립내역 ID"),
                                                        fieldWithPath("stampHistory[].cafeName").description("카페 이름"),
                                                        fieldWithPath("stampHistory[].stampCount").description("스탬프 적립개수 "),
                                                        fieldWithPath("stampHistory[].createdAt").description("스탬프 적립일")
                                                )
                                                .responseSchema(Schema.schema("CustomerStampHistoriesFindResponse"))
                                                .build()
                                )
                        )
                )
                .andExpect(status().isOk());
    }
}
