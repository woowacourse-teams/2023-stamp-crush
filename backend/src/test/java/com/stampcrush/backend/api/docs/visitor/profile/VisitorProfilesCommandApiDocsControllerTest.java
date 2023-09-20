package com.stampcrush.backend.api.docs.visitor.profile;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.Schema;
import com.stampcrush.backend.api.docs.DocsControllerTest;
import com.stampcrush.backend.api.visitor.profile.VisitorProfilesLinkDataRequest;
import com.stampcrush.backend.api.visitor.profile.request.VisitorProfilesPhoneNumberUpdateRequest;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;

import java.util.Optional;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class VisitorProfilesCommandApiDocsControllerTest extends DocsControllerTest {

    @Test
    void 고객이_전화번호_등록() throws Exception {
        // given
        when(customerRepository.findById(CUSTOMER.getId())).thenReturn(Optional.of(CUSTOMER));
        when(customerRepository.findByLoginId(CUSTOMER.getLoginId())).thenReturn(Optional.of(CUSTOMER));
        VisitorProfilesPhoneNumberUpdateRequest request = new VisitorProfilesPhoneNumberUpdateRequest("01012345678");
        when(authTokensGenerator.isValidToken(anyString())).thenReturn(true);
        when(authTokensGenerator.extractMemberId(anyString())).thenReturn(CUSTOMER.getId());
        // when, then
        mockMvc.perform(
                        RestDocumentationRequestBuilders.post("/api/profiles/phone-number")
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON)
                                .header(HttpHeaders.AUTHORIZATION, CUSTOMER_BEARER_HEADER))
                .andDo(document("visitor/profiles/post-phonenumber",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                resource(
                                        ResourceSnippetParameters.builder()
                                                .tag("고객 모드")
                                                .description("고객이 전화번호 등록")
                                                .requestHeaders(headerWithName("Authorization").description("Bearer"))
                                                .requestFields(fieldWithPath("phoneNumber").description("고객이 등록할 전화번호"))
                                                .requestSchema(Schema.schema("VisitorProfilesPhoneNumberUpdateRequest"))
                                                .build()
                                )
                        )
                )
                .andExpect(status().isOk());
    }

    @Test
    void 임시회원_가입회원_연동() throws Exception {
        // given
        when(customerRepository.findById(CUSTOMER.getId())).thenReturn(Optional.of(CUSTOMER));
        when(customerRepository.findByLoginId(CUSTOMER.getLoginId())).thenReturn(Optional.of(CUSTOMER));

        VisitorProfilesLinkDataRequest request = new VisitorProfilesLinkDataRequest(1L);
        when(authTokensGenerator.isValidToken(anyString())).thenReturn(true);
        when(authTokensGenerator.extractMemberId(anyString())).thenReturn(CUSTOMER.getId());
        // when, then
        mockMvc.perform(
                        RestDocumentationRequestBuilders.post("/api/profiles/link-data")
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON)
                                .header(HttpHeaders.AUTHORIZATION, CUSTOMER_BEARER_HEADER))
                .andDo(document("visitor/profiles/link-data",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                resource(
                                        ResourceSnippetParameters.builder()
                                                .tag("고객 모드")
                                                .description("가입 고객 임시 고객 데이터 연동")
                                                .requestHeaders(headerWithName("Authorization").description("Bearer"))
                                                .requestSchema(Schema.schema("VisitorProfilesLinkDataRequest"))
                                                .build()
                                )
                        )
                )
                .andExpect(status().isOk());
    }
}
