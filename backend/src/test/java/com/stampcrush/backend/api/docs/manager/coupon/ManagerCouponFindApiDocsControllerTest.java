package com.stampcrush.backend.api.docs.manager.coupon;

import com.epages.restdocs.apispec.ResourceDocumentation;
import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.Schema;
import com.stampcrush.backend.api.docs.DocsControllerTest;
import com.stampcrush.backend.application.manager.coupon.dto.CafeCustomerFindResultDto;
import com.stampcrush.backend.application.manager.coupon.dto.CustomerAccumulatingCouponFindResultDto;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ManagerCouponFindApiDocsControllerTest extends DocsControllerTest {

    @Test
    void 고객의_쿠폰_조회() throws Exception {
        // given
        Long customerId = 1L;
        Long cafeId = 1L;

        when(ownerRepository.findByLoginId(OWNER.getLoginId())).thenReturn(Optional.of(OWNER));
        when(managerCouponFindService.findAccumulatingCoupon(cafeId, customerId)).thenReturn(List.of(new CustomerAccumulatingCouponFindResultDto(1L, 1L, "윤생", 3, LocalDateTime.MIN, false, 10)));

        // when, then
        mockMvc.perform(RestDocumentationRequestBuilders.get("/api/admin/customers/{customerId}/coupons", customerId)
                        .queryParam("cafe-id", String.valueOf(cafeId))
                        .queryParam("active", "true")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, OWNER_BASIC_HEADER))
                .andDo(document("manager/coupon/find-coupon",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(
                                ResourceSnippetParameters.builder()
                                        .tag("사장 모드")
                                        .description("고객의 쿠폰 조회")
                                        .requestHeaders(headerWithName("Authorization").description("임시(Basic)"))
                                        .queryParameters(ResourceDocumentation.parameterWithName("cafe-id").description("카페 Id"),
                                                ResourceDocumentation.parameterWithName("active").description("true(활성화된 쿠폰만 조회)"))
                                        .responseFields(
                                                fieldWithPath("coupons[].id").description("쿠폰 ID"),
                                                fieldWithPath("coupons[].customerId").description("고객 ID"),
                                                fieldWithPath("coupons[].nickname").description("닉네임"),
                                                fieldWithPath("coupons[].stampCount").description("스탬프 개수"),
                                                fieldWithPath("coupons[].expireDate").description("유효기간"),
                                                fieldWithPath("coupons[].isPrevious").description("지난 정책의 쿠폰인지 여부"),
                                                fieldWithPath("coupons[].maxStampCount").description("최대 스탬프 개수"))
                                        .responseSchema(Schema.schema("CustomerAccumulatingCouponsFindResponse"))
                                        .build())))
                .andExpect(status().isOk());
    }

    @Test
    void 고객_목록_조회() throws Exception {
        // given
        Long cafeId = 1L;
        when(ownerRepository.findByLoginId(OWNER.getLoginId())).thenReturn(Optional.of(OWNER));
        when(managerCouponFindService.findCouponsByCafe(cafeId)).thenReturn(List.of(new CafeCustomerFindResultDto(1L, "레오", 3, 12, 30, LocalDateTime.MIN, true, 10)));

        // when, then
        mockMvc.perform(RestDocumentationRequestBuilders.get("/api/admin/cafes/{cafeId}/customers", cafeId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, OWNER_BASIC_HEADER))
                .andDo(document("manager/coupon/find-customer-list",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(
                                ResourceSnippetParameters.builder()
                                        .tag("사장 모드")
                                        .description("고객 목록 조회")
                                        .requestHeaders(headerWithName("Authorization").description("임시(Basic)"))
                                        .responseFields(
                                                fieldWithPath("customers[].id").description("고객 ID"),
                                                fieldWithPath("customers[].nickname").description("닉네임"),
                                                fieldWithPath("customers[].stampCount").description("스탬프 개수"),
                                                fieldWithPath("customers[].rewardCount").description("리워드 개수"),
                                                fieldWithPath("customers[].visitCount").description("방문 횟수"),
                                                fieldWithPath("customers[].firstVisitDate").description("첫 방문 날짜"),
                                                fieldWithPath("customers[].isRegistered").description("임시/가입 회원 여부"),
                                                fieldWithPath("customers[].maxStampCount").description("최대 스탬프 개수"))
                                        .responseSchema(Schema.schema("CafeCustomersFindResponse"))
                                        .build())))
                .andExpect(status().isOk());
    }
}
