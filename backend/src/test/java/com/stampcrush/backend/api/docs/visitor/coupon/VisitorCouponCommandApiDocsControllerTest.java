package com.stampcrush.backend.api.docs.visitor.coupon;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.stampcrush.backend.api.docs.DocsControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;

import java.util.Optional;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class VisitorCouponCommandApiDocsControllerTest extends DocsControllerTest {

    @Test
    void 쿠폰_삭제() throws Exception {
        // given
        when(customerRepository.findById(CUSTOMER.getId())).thenReturn(Optional.of(CUSTOMER));
        when(customerRepository.findByLoginId(CUSTOMER.getLoginId()))
                .thenReturn(Optional.of(CUSTOMER));
        when(authTokensGenerator.isValidToken(anyString())).thenReturn(true);
        when(authTokensGenerator.extractMemberId(anyString())).thenReturn(CUSTOMER.getId());

        doNothing()
                .when(visitorCouponCommandService)
                .deleteCoupon(any(), any());

        mockMvc.perform(
                        RestDocumentationRequestBuilders.delete("/api/coupons/{couponId}", 1L)
                                .contentType(APPLICATION_JSON)
                                .header(AUTHORIZATION, CUSTOMER_BEARER_HEADER)
                )
                .andDo(document("visitor/coupon/delete-coupon",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                resource(
                                        ResourceSnippetParameters.builder()
                                                .tag("고객 모드")
                                                .description("쿠폰 삭제")
                                                .requestHeaders(headerWithName("Authorization").description("Bearer"))
                                                .build()
                                )
                        )
                )
                .andExpect(status().isNoContent());
    }
}
