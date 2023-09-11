package com.stampcrush.backend.api.docs.manager.cafe;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.Schema;
import com.stampcrush.backend.api.docs.DocsControllerTest;
import com.stampcrush.backend.api.manager.cafe.request.CafeCreateRequest;
import com.stampcrush.backend.api.manager.cafe.request.CafeUpdateRequest;
import com.stampcrush.backend.application.manager.cafe.dto.CafeCreateDto;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;

import java.time.LocalTime;
import java.util.Optional;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ManagerCafeCommandApiDocsControllerTest extends DocsControllerTest {

    @Test
    void 카페_상세_정보_변경() throws Exception {
        // given
        when(ownerRepository.findByLoginId(OWNER.getLoginId())).thenReturn(Optional.of(OWNER));
        when(cafeRepository.findById(CAFE_ID)).thenReturn(Optional.of(CAFE));
        CafeUpdateRequest request = new CafeUpdateRequest("안녕하세요", LocalTime.NOON, LocalTime.MIDNIGHT, "01012345678", "imageUrl");

        // when, then
        mockMvc.perform(RestDocumentationRequestBuilders.patch("/api/admin/cafes/{cafeId}", CAFE_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .header(HttpHeaders.AUTHORIZATION, OWNER_BASIC_HEADER))
                .andDo(document("manager/cafe/update-cafe",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                resource(
                                        ResourceSnippetParameters.builder()
                                                .tag("사장 모드")
                                                .description("카페 상세 정보 업데이트")
                                                .requestHeaders(headerWithName("Authorization").description("임시(Basic)"))
                                                .requestFields(
                                                        fieldWithPath("openTime").description("오픈 시간"),
                                                        fieldWithPath("closeTime").description("마감 시간"),
                                                        fieldWithPath("telephoneNumber").description("전화번호"),
                                                        fieldWithPath("cafeImageUrl").description("카페 이미지 URL"),
                                                        fieldWithPath("introduction").description("카페 소개글")
                                                )
                                                .requestSchema(Schema.schema("CafeUpdateRequest"))
                                                .build()
                                )
                        )
                )
                .andExpect(status().isOk());
    }

    @Test
    void 카페_등록() throws Exception {
        // given
        when(ownerRepository.findByLoginId(OWNER.getLoginId())).thenReturn(Optional.of(OWNER));
        CafeCreateRequest cafeCreateRequest = new CafeCreateRequest("우아한카페", "서울시 잠실", "루터회관 13층", "000-111-222");
        CafeCreateDto cafeCreateDto = new CafeCreateDto(
                OWNER.getId(),
                cafeCreateRequest.getName(),
                cafeCreateRequest.getRoadAddress(),
                cafeCreateRequest.getDetailAddress(),
                cafeCreateRequest.getBusinessRegistrationNumber());
        when(managerCafeCommandService.createCafe(cafeCreateDto)).thenReturn(1L);

        // when, then
        mockMvc.perform(RestDocumentationRequestBuilders.post("/api/admin/cafes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, OWNER_BASIC_HEADER)
                        .content(objectMapper.writeValueAsString(cafeCreateRequest)))
                .andDo(document("manager/cafe/register-cafe",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                resource(
                                        ResourceSnippetParameters.builder()
                                                .tag("사장 모드")
                                                .description("카페 등록")
                                                .requestHeaders(headerWithName("Authorization").description("임시(Basic)"))
                                                .requestFields(fieldWithPath("name").description("카페 이름"),
                                                        fieldWithPath("roadAddress").description("도로명 주소"),
                                                        fieldWithPath("detailAddress").description("상세 주소"),
                                                        fieldWithPath("businessRegistrationNumber").description("사업자 등록번호"))
                                                .requestSchema(Schema.schema("CafeCreateRequest"))
                                                .responseHeaders(headerWithName("Location").description("/cafes/{cafesId}"))
                                                .build()
                                )
                        )
                )
                .andExpect(status().isCreated());
    }
}
