package com.stampcrush.backend.api.docs.visitor.profile;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.stampcrush.backend.api.docs.DocsControllerTest;
import com.stampcrush.backend.application.visitor.profile.VisitorCancelMembershipCommandService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.util.Optional;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class VisitorCancelMembershipCommandApiDocsController extends DocsControllerTest {

    @MockBean
    private VisitorCancelMembershipCommandService visitorCancelMembershipCommandService;

    @Test
    void 고객_회원_탈퇴() throws Exception {
        when(customerRepository.findById(CUSTOMER.getId()))
                .thenReturn(Optional.of(CUSTOMER));
        when(customerRepository.findByLoginId(CUSTOMER.getLoginId()))
                .thenReturn(Optional.of(CUSTOMER));
        when(authTokensGenerator.isValidToken(anyString()))
                .thenReturn(true);
        when(authTokensGenerator.extractMemberId(anyString()))
                .thenReturn(CUSTOMER.getId());
        doNothing()
                .when(visitorCancelMembershipCommandService)
                .cancelMembership(CUSTOMER.getId());

        mockMvc.perform(
                        delete("/api/customers")
                                .contentType(MediaType.APPLICATION_JSON)
                                .header(HttpHeaders.AUTHORIZATION, CUSTOMER_BEARER_HEADER))
                .andDo(document("visitor/profiles/cancel-membership",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                resource(
                                        ResourceSnippetParameters.builder()
                                                .tag("고객 모드")
                                                .description("고객 회원 탈퇴")
                                                .requestHeaders(headerWithName("Authorization").description("Bearer"))
                                                .build()
                                )
                        )
                )
                .andExpect(status().isNoContent());
    }
}
