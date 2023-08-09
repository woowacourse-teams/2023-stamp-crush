package com.stampcrush.backend.api.docs.visitor.coupon;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.Schema;
import com.stampcrush.backend.api.docs.DocsControllerTest;
import com.stampcrush.backend.application.visitor.coupon.dto.CustomerCouponFindResultDto;
import com.stampcrush.backend.fixture.CafeFixture;
import com.stampcrush.backend.fixture.CouponFixture;
import org.junit.jupiter.api.Test;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;

import java.util.List;
import java.util.Optional;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class VisitorCouponFindApiDocsControllerTest extends DocsControllerTest {

    @Test
    void 고객의_쿠폰_조회_요청_시_인증이_되면_200_상태코드와_응답을_반환한다() throws Exception {
        when(customerRepository.findByLoginId(CUSTOMER.getLoginId()))
                .thenReturn(Optional.of(CUSTOMER));

        when(visitorCouponFindService.findOneCouponForOneCafe(CUSTOMER.getId()))
                .thenReturn(
                        List.of(
                                CustomerCouponFindResultDto.of(
                                        CafeFixture.GITCHAN_CAFE,
                                        CouponFixture.GITCHAN_CAFE_COUPON,
                                        true,
                                        CouponFixture.GITCHAN_CAFE_COUPON_STAMP_COORDINATE
                                )
                        )
                );

        mockMvc.perform(
                        RestDocumentationRequestBuilders.get("/api/coupons")
                                .contentType(APPLICATION_JSON)
                                .header(AUTHORIZATION, CUSTOMER_BASIC_HEADER)
                )
                .andDo(document("visitor/coupon/coupon-list",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                resource(
                                        ResourceSnippetParameters.builder()
                                                .tag("고객 모드")
                                                .description("쿠폰 리스트 조회")
                                                .requestHeaders(headerWithName("Authorization").description("임시(Basic)"))
                                                .responseFields(
                                                        fieldWithPath("coupons[].cafeInfo.id").description("카페 ID"),
                                                        fieldWithPath("coupons[].cafeInfo.name").description("카페 이름"),
                                                        fieldWithPath("coupons[].cafeInfo.isFavorites").description("카페 즐겨찾기 여부"),
                                                        fieldWithPath("coupons[].couponInfos[].id").description("쿠폰 ID"),
                                                        fieldWithPath("coupons[].couponInfos[].status").description("쿠폰 상태 (ACCUMULATING, REWARDED, EXPIRED)"),
                                                        fieldWithPath("coupons[].couponInfos[].stampCount").description("스탬프 개수"),
                                                        fieldWithPath("coupons[].couponInfos[].maxStampCount").description("최대 스탬프 개수"),
                                                        fieldWithPath("coupons[].couponInfos[].rewardName").description("보상 이름"),
                                                        fieldWithPath("coupons[].couponInfos[].frontImageUrl").description("쿠폰 앞면 이미지 URL"),
                                                        fieldWithPath("coupons[].couponInfos[].backImageUrl").description("쿠폰 뒷면 이미지 URL"),
                                                        fieldWithPath("coupons[].couponInfos[].stampImageUrl").description("스탬프 이미지 URL"),
                                                        fieldWithPath("coupons[].couponInfos[].coordinates[].order").description("좌표 순서"),
                                                        fieldWithPath("coupons[].couponInfos[].coordinates[].xCoordinate").description("X 좌표"),
                                                        fieldWithPath("coupons[].couponInfos[].coordinates[].yCoordinate").description("Y 좌표")
                                                )
                                                .responseSchema(Schema.schema("CustomerCouponsFindResponse"))
                                                .build()
                                )
                        )
                )
                .andExpect(status().isOk());
    }
}
