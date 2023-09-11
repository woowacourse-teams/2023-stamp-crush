package com.stampcrush.backend.api.docs.manager.cafe;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.Schema;
import com.stampcrush.backend.api.docs.DocsControllerTest;
import com.stampcrush.backend.application.manager.cafe.dto.CafeCouponCoordinateFindResultDto;
import com.stampcrush.backend.application.manager.cafe.dto.CafeCouponSettingFindResultDto;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;

import java.util.List;
import java.util.Optional;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.ResourceDocumentation.parameterWithName;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ManagerCafeCouponSettingFindApiDocsControllerTest extends DocsControllerTest {

    @Test
    void 현재_카페의_쿠폰_디자인_정책_조회() throws Exception {
        // given
        Long CAFE_ID = 1L;
        Long ownerId = OWNER.getId();

        when(ownerRepository.findByLoginId(OWNER.getLoginId())).thenReturn(Optional.of(OWNER));
        when(ownerRepository.findById(ownerId)).thenReturn(Optional.of(OWNER));
        when(cafeRepository.findById(CAFE_ID)).thenReturn(Optional.of(CAFE));
        when(managerCafeCouponSettingFindService.findCafeCouponSetting(ownerId, CAFE_ID)).thenReturn(
                new CafeCouponSettingFindResultDto("frontImageUrl", "backImageUrl",
                        "stampImageUrl", List.of(
                        new CafeCouponCoordinateFindResultDto(1, 1, 1),
                        new CafeCouponCoordinateFindResultDto(2, 2, 2),
                        new CafeCouponCoordinateFindResultDto(3, 3, 3),
                        new CafeCouponCoordinateFindResultDto(4, 4, 4),
                        new CafeCouponCoordinateFindResultDto(5, 5, 5))
                ));
        when(authTokensGenerator.isValidToken(anyString())).thenReturn(true);
        when(authTokensGenerator.extractMemberId(anyString())).thenReturn(ownerId);

        // when, then
        mockMvc.perform(RestDocumentationRequestBuilders.get("/api/admin/coupon-setting?cafe-id=" + CAFE_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, OWNER_BEARER_HEADER))
                .andDo(document("manager/cafe/find-current-cafe-coupon-design",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                resource(
                                        ResourceSnippetParameters.builder()
                                                .tag("사장 모드")
                                                .description("카페의 현재 쿠폰 디자인 조회")
                                                .requestHeaders(headerWithName("Authorization").description("임시(Basic)"))
                                                .queryParameters(parameterWithName("cafe-id").description("카페 Id"))
                                                .responseFields(
                                                        fieldWithPath("frontImageUrl").description("프론트 이미지 URL"),
                                                        fieldWithPath("backImageUrl").description("백 이미지 URL"),
                                                        fieldWithPath("stampImageUrl").description("스탬프 이미지 URL"),
                                                        fieldWithPath("coordinates[].order").description("스탬프 좌표 순서"),
                                                        fieldWithPath("coordinates[].xCoordinate").description("스탬프 x 좌표"),
                                                        fieldWithPath("coordinates[].yCoordinate").description("스탬프 y 좌표")
                                                )
                                                .responseSchema(Schema.schema("CafeCouponSettingFindResponse"))
                                                .build()
                                )
                        )
                )
                .andExpect(status().isOk());
    }

    @Test
    void 쿠폰이_발급된_당시의_쿠폰_디자인_정책_조회() throws Exception {
        // given
        Long CAFE_ID = 1L;
        Long COUPON_ID = 1L;
        Long ownerId = OWNER.getId();

        when(ownerRepository.findByLoginId(OWNER.getLoginId())).thenReturn(Optional.of(OWNER));
        when(ownerRepository.findById(ownerId)).thenReturn(Optional.of(OWNER));
        when(cafeRepository.findById(CAFE_ID)).thenReturn(Optional.of(CAFE));
        when(managerCafeCouponSettingFindService.findCouponSetting(ownerId, CAFE_ID, COUPON_ID)).thenReturn(
                new CafeCouponSettingFindResultDto("frontImageUrl", "backImageUrl",
                        "stampImageUrl", List.of(
                        new CafeCouponCoordinateFindResultDto(1, 1, 1),
                        new CafeCouponCoordinateFindResultDto(2, 2, 2),
                        new CafeCouponCoordinateFindResultDto(3, 3, 3),
                        new CafeCouponCoordinateFindResultDto(4, 4, 4),
                        new CafeCouponCoordinateFindResultDto(5, 5, 5))
                ));
        when(authTokensGenerator.isValidToken(anyString())).thenReturn(true);
        when(authTokensGenerator.extractMemberId(anyString())).thenReturn(ownerId);

        // when, then
        mockMvc.perform(RestDocumentationRequestBuilders.get("/api/admin/coupon-setting/{couponId}?cafe-id=" + CAFE_ID, COUPON_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, OWNER_BEARER_HEADER))
//                        .header(HttpHeaders.AUTHORIZATION, OWNER_BASIC_HEADER))
                .andDo(document("manager/cafe/find-coupon-design-when-coupon-issued",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                resource(
                                        ResourceSnippetParameters.builder()
                                                .tag("사장 모드")
                                                .description("쿠폰이 발급될때의 쿠폰 디자인 조회")
                                                .requestHeaders(headerWithName("Authorization").description("임시(Basic)"))
                                                .queryParameters(parameterWithName("cafe-id").description("카페 Id"))
                                                .responseFields(
                                                        fieldWithPath("frontImageUrl").description("프론트 이미지 URL"),
                                                        fieldWithPath("backImageUrl").description("백 이미지 URL"),
                                                        fieldWithPath("stampImageUrl").description("스탬프 이미지 URL"),
                                                        fieldWithPath("coordinates[].order").description("스탬프 좌표 순서"),
                                                        fieldWithPath("coordinates[].xCoordinate").description("스탬프 x 좌표"),
                                                        fieldWithPath("coordinates[].yCoordinate").description("스탬프 y 좌표")
                                                )
                                                .responseSchema(Schema.schema("CafeCouponSettingFindResponse"))
                                                .build()
                                )
                        )
                )
                .andExpect(status().isOk());
    }
}
