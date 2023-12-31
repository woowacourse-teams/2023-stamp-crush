package com.stampcrush.backend.api.docs.manager.cafe;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.Schema;
import com.stampcrush.backend.api.docs.DocsControllerTest;
import com.stampcrush.backend.application.manager.cafe.dto.CafeFindResultDto;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ManagerCafeFindApiDocsControllerTest extends DocsControllerTest {

    @Test
    void 카페_조회_요청_사장_모드() throws Exception {
        // given
        when(ownerRepository.findByLoginId(OWNER.getLoginId())).thenReturn(Optional.of(OWNER));
        when(ownerRepository.findById(OWNER.getId())).thenReturn(Optional.of(OWNER));
        when(managerCafeFindService.findCafesByOwner(OWNER.getId())).thenReturn(List.of(new CafeFindResultDto(1L, "우아한카페", LocalTime.MIDNIGHT, LocalTime.NOON, "01012345678", "http://imge.co", "서울시 송파구", "루터회관", "032-1234-87", "안녕하세요")));
        when(authTokensGenerator.isValidToken(anyString())).thenReturn(true);
        when(authTokensGenerator.extractMemberId(anyString())).thenReturn(OWNER.getId());

        // when, then
        mockMvc.perform(RestDocumentationRequestBuilders.get("/api/admin/cafes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, OWNER_BEARER_HEADER))
                .andDo(document("manager/cafe/find-cafe",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                resource(
                                        ResourceSnippetParameters.builder()
                                                .tag("사장 모드")
                                                .description("카페 정보 조회")
                                                .requestHeaders(headerWithName("Authorization").description("Bearer"))
                                                .responseFields(
                                                        fieldWithPath("cafes[].id").description("카페 ID"),
                                                        fieldWithPath("cafes[].name").description("카페 이름"),
                                                        fieldWithPath("cafes[].openTime").description("카페 오픈 시간"),
                                                        fieldWithPath("cafes[].closeTime").description("카페 닫는 시간"),
                                                        fieldWithPath("cafes[].telephoneNumber").description("카페 전화번호"),
                                                        fieldWithPath("cafes[].cafeImageUrl").description("카페 이미지 URL"),
                                                        fieldWithPath("cafes[].roadAddress").description("카페 도로명주소"),
                                                        fieldWithPath("cafes[].detailAddress").description("카페 상세 주소"),
                                                        fieldWithPath("cafes[].businessRegistrationNumber").description("카페 도로명주소"),
                                                        fieldWithPath("cafes[].introduction").description("카페 소개글")
                                                )
                                                .responseSchema(Schema.schema("CafesFindResponse"))
                                                .build()
                                )
                        )
                )
                .andExpect(status().isOk());
    }
}
