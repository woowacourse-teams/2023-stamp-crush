package com.stampcrush.backend.api.docs.manager.customer;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.Schema;
import com.stampcrush.backend.api.docs.DocsControllerTest;
import com.stampcrush.backend.api.manager.customer.request.TemporaryCustomerCreateRequest;
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

class ManagerCustomerCommandApiDocsControllerTest extends DocsControllerTest {

    @Test
    void 임시_고객_생성_요청_사장_모드() throws Exception {
        // given
        when(ownerRepository.findById(OWNER.getId())).thenReturn(Optional.of(OWNER));
        when(ownerRepository.findByLoginId(OWNER.getLoginId())).thenReturn(Optional.of(OWNER));
        TemporaryCustomerCreateRequest request = new TemporaryCustomerCreateRequest("01011112222");
        when(managerCustomerCommandService.createTemporaryCustomer(request.getPhoneNumber())).thenReturn(1L);
        when(authTokensGenerator.isValidToken(anyString())).thenReturn(true);
        when(authTokensGenerator.extractMemberId(anyString())).thenReturn(OWNER.getId());

        // when, then
        mockMvc.perform(RestDocumentationRequestBuilders.post("/api/admin/temporary-customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, OWNER_BEARER_HEADER)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(document("manager/customer/create-temporary-customer",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                resource(
                                        ResourceSnippetParameters.builder()
                                                .tag("사장 모드")
                                                .description("임시 고객 생성")
                                                .requestHeaders(headerWithName("Authorization").description("임시(Basic)"))
                                                .requestFields(fieldWithPath("phoneNumber").description("고객 전화번호"))
                                                .requestSchema(Schema.schema("TemporaryCustomerCreateRequest"))
                                                .responseHeaders(headerWithName("Location").description("/customers/{customerId}"))
                                                .build()
                                )
                        )
                )
                .andExpect(status().isCreated());
    }
}
