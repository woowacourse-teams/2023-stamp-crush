package com.stampcrush.backend.api.docs.manager.cafe;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.Schema;
import com.stampcrush.backend.api.docs.DocsControllerTest;
import com.stampcrush.backend.api.manager.cafe.request.CafeCouponSettingUpdateRequest;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;

import java.util.List;
import java.util.Optional;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.ResourceDocumentation.parameterWithName;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ManagerCafeCouponSettingCommandApiDocsControllerTest extends DocsControllerTest {

    @Test
    void 쿠폰_디자인_및_정책_수정() throws Exception {
        // given
        when(ownerRepository.findByLoginId(OWNER.getLoginId())).thenReturn(Optional.of(OWNER));
        when(cafeRepository.findById(CAFE_ID)).thenReturn(Optional.of(CAFE));
        CafeCouponSettingUpdateRequest request = new CafeCouponSettingUpdateRequest(
                "frontImageUrl",
                "backImageUrl",
                "stampImageUrl",
                List.of(new CafeCouponSettingUpdateRequest.CouponStampCoordinateRequest(1, 1, 1)),
                "reward",
                6
        );

        // when, then
        mockMvc.perform(RestDocumentationRequestBuilders.post("/api/admin/coupon-setting")
                        .param("cafe-id", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .header(HttpHeaders.AUTHORIZATION, OWNER_BASIC_HEADER))
                .andDo(document("manager/cafe/cafe-coupon-setting",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                resource(
                                        ResourceSnippetParameters.builder()
                                                .tag("사장 모드")
                                                .description("쿠폰 디자인 및 정책 수정")
                                                .requestHeaders(headerWithName("Authorization").description("임시(Basic)"))
                                                .queryParameters(parameterWithName("cafe-id").description("카페 ID"))
                                                .requestFields(
                                                        fieldWithPath("frontImageUrl").description("쿠폰 앞면 이미지 URL"),
                                                        fieldWithPath("backImageUrl").description("쿠폰 뒷면 이미지 URL"),
                                                        fieldWithPath("stampImageUrl").description("스탬프 이미지 URL"),
                                                        fieldWithPath("coordinates[].order").description("좌표 순서"),
                                                        fieldWithPath("coordinates[].xCoordinate").description("X 좌표"),
                                                        fieldWithPath("coordinates[].yCoordinate").description("Y 좌표"),
                                                        fieldWithPath("reward").description("보상 이름"),
                                                        fieldWithPath("expirePeriod").description("유효기간 (유효기간 미설정시 1200)")
                                                )
                                                .requestSchema(Schema.schema("CafeCouponSettingUpdateRequest"))
                                                .build()
                                )
                        )
                )
                .andExpect(status().isNoContent());
    }
}
