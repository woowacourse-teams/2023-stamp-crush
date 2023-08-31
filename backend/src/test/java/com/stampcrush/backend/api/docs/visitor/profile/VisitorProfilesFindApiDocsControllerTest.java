package com.stampcrush.backend.api.docs.visitor.profile;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.Schema;
import com.stampcrush.backend.api.docs.DocsControllerTest;
import com.stampcrush.backend.application.visitor.profile.dto.VisitorProfileFindResultDto;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;

import java.util.Optional;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class VisitorProfilesFindApiDocsControllerTest extends DocsControllerTest {

    @Test
    void 고객의_프로필_조회() throws Exception {
        // given
        when(customerRepository.findByLoginId(CUSTOMER.getLoginId())).thenReturn(Optional.of(CUSTOMER));
        when(visitorProfilesFindService.findVisitorProfile(CUSTOMER.getId()))
                .thenReturn(
                        new VisitorProfileFindResultDto(CUSTOMER.getId(), "jena", "01012345678",
                                "yenawee@gmail.com")
                );

        // when, then
        mockMvc.perform(
                        RestDocumentationRequestBuilders.get("/api/profiles")
                                .contentType(MediaType.APPLICATION_JSON)
                                .header(HttpHeaders.AUTHORIZATION, CUSTOMER_BASIC_HEADER))
                .andDo(document("visitor/profiles/find-profiles",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                resource(
                                        ResourceSnippetParameters.builder()
                                                .tag("고객 모드")
                                                .description("고객 프로필 조회")
                                                .requestHeaders(headerWithName("Authorization").description("임시(Basic)"))
                                                .responseFields(
                                                        fieldWithPath("profile.id").description("고객 ID"),
                                                        fieldWithPath("profile.nickname").description("고객 닉네임"),
                                                        fieldWithPath("profile.phoneNumber").description("고객 전화번호"),
                                                        fieldWithPath("profile.email").description("고객 이메일")
                                                )
                                                .requestSchema(Schema.schema("VisitorProfilesFindResponse"))
                                                .build()
                                )
                        )
                )
                .andExpect(status().isOk());
    }
}
