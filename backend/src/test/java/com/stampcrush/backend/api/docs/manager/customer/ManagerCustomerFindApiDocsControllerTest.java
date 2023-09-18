package com.stampcrush.backend.api.docs.manager.customer;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.Schema;
import com.stampcrush.backend.api.docs.DocsControllerTest;
import com.stampcrush.backend.application.manager.customer.dto.CustomerFindDto;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;

import java.util.List;
import java.util.Optional;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ManagerCustomerFindApiDocsControllerTest extends DocsControllerTest {

    @Test
    void 고객_조회_요청_사장_모드() throws Exception {
        // given
        when(ownerRepository.findById(OWNER.getId())).thenReturn(Optional.of(OWNER));
        when(ownerRepository.findByLoginId(OWNER.getLoginId())).thenReturn(Optional.of(OWNER));
        when(managerCustomerFindService.findCustomer("01012345678")).thenReturn(List.of(new CustomerFindDto(1L, "윤생1234", "01012345678")));
        when(authTokensGenerator.isValidToken(anyString())).thenReturn(true);
        when(authTokensGenerator.extractMemberId(anyString())).thenReturn(OWNER.getId());
        // when, then
        mockMvc.perform(RestDocumentationRequestBuilders.get("/api/admin/customers")
                        .param("phone-number", "01012345678")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, OWNER_BEARER_HEADER))
                .andDo(document("manager/customer/find-customer",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                resource(
                                        ResourceSnippetParameters.builder()
                                                .tag("사장 모드")
                                                .description("전화번호로 고객 조회")
                                                .requestHeaders(headerWithName("Authorization").description("Bearer"))
                                                .queryParameters(parameterWithName("phone-number").description("입력한 전화번호"))
                                                .responseFields(
                                                        fieldWithPath("customer[].id").description("고객 ID"),
                                                        fieldWithPath("customer[].nickname").description("고객 닉네임"),
                                                        fieldWithPath("customer[].phoneNumber").description("고객 전화번호")
                                                )
                                                .responseSchema(Schema.schema("CustomersFindResponse"))
                                                .build()
                                )
                        )
                )
                .andExpect(status().isOk());
    }
}
