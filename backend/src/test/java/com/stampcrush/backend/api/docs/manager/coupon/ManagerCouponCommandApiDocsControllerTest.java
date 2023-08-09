package com.stampcrush.backend.api.docs.manager.coupon;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.Schema;
import com.stampcrush.backend.api.docs.DocsControllerTest;
import com.stampcrush.backend.api.manager.coupon.request.CouponCreateRequest;
import com.stampcrush.backend.api.manager.coupon.request.StampCreateRequest;
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

public class ManagerCouponCommandApiDocsControllerTest extends DocsControllerTest {

    @Test
    void 쿠폰_신규_발급() throws Exception {
        // given
        Long cafeId = 1L;
        Long customerId = 1L;
        when(ownerRepository.findByLoginId(OWNER.getLoginId())).thenReturn(Optional.of(OWNER));
        CouponCreateRequest request = new CouponCreateRequest(cafeId);
        when(managerCouponCommandService.createCoupon(cafeId, customerId)).thenReturn(1L);

        // when, then
        mockMvc.perform(RestDocumentationRequestBuilders.post("/api/admin/customers/{customerId}/coupons", customerId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, OWNER_BASIC_HEADER)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(document("manager/coupon/create-coupon",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                resource(
                                        ResourceSnippetParameters.builder()
                                                .tag("사장 모드")
                                                .description("쿠폰 신규 발급")
                                                .requestHeaders(headerWithName("Authorization").description("임시(Basic)"))
                                                .requestFields(fieldWithPath("cafeId").description("카페 ID"))
                                                .requestSchema(Schema.schema("CouponCreateRequest"))
                                                .responseFields(fieldWithPath("couponId").description("쿠폰 ID"))
                                                .responseSchema(Schema.schema("CouponCreateResponse"))
                                                .build()
                                )
                        )
                )
                .andExpect(status().isCreated());
    }

    @Test
    void 스탬프_적립() throws Exception {
        // given
        Long couponId = 1L;
        Long customerId = 1L;
        when(ownerRepository.findByLoginId(OWNER.getLoginId())).thenReturn(Optional.of(OWNER));
        StampCreateRequest request = new StampCreateRequest(3);

        // when, then
        mockMvc.perform(RestDocumentationRequestBuilders.post("/api/admin/customers/{customerId}/coupons/{couponId}/stamps", customerId, couponId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, OWNER_BASIC_HEADER)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(document("manager/coupon/create-stamp",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                resource(
                                        ResourceSnippetParameters.builder()
                                                .tag("사장 모드")
                                                .description("스탬프 적립")
                                                .requestHeaders(headerWithName("Authorization").description("임시(Basic)"))
                                                .requestFields(fieldWithPath("earningStampCount").description("적립할 스탬프 개수"))
                                                .requestSchema(Schema.schema("StampCreateRequest"))
                                                .build()
                                )
                        )
                )
                .andExpect(status().isCreated());
    }
}
