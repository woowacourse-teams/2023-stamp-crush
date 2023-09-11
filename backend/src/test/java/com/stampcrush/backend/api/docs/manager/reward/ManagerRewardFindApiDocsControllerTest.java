package com.stampcrush.backend.api.docs.manager.reward;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.Schema;
import com.stampcrush.backend.api.docs.DocsControllerTest;
import com.stampcrush.backend.application.manager.reward.dto.RewardFindDto;
import com.stampcrush.backend.application.manager.reward.dto.RewardFindResultDto;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;

import java.util.List;
import java.util.Optional;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.ResourceDocumentation.parameterWithName;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ManagerRewardFindApiDocsControllerTest extends DocsControllerTest {

    @Test
    void 고객의_리워드_조회() throws Exception {
        // given
        Long customerId = 1L;
        Long cafeId = 1L;
        Long ownerId = OWNER.getId();

        when(ownerRepository.findByLoginId(OWNER.getLoginId())).thenReturn(Optional.of(OWNER));
        when(ownerRepository.findById(ownerId)).thenReturn(Optional.of(OWNER));
        when(cafeRepository.findById(CAFE_ID)).thenReturn(Optional.of(CAFE));
        when(managerRewardFindService.findRewards(any(), any(RewardFindDto.class))).thenReturn(List.of(new RewardFindResultDto(1L, "아메리카노"), new RewardFindResultDto(2L, "조각케익")));
        when(authTokensGenerator.isValidToken(anyString())).thenReturn(true);
        when(authTokensGenerator.extractMemberId(anyString())).thenReturn(ownerId);

        // when, then
        mockMvc.perform(RestDocumentationRequestBuilders.get("/api/admin/customers/{customerId}/rewards", customerId)
                        .queryParam("cafe-id", String.valueOf(cafeId))
                        .queryParam("used", "false")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, OWNER_BEARER_HEADER))
//                        .header(HttpHeaders.AUTHORIZATION, OWNER_BASIC_HEADER))
                .andDo(document("manager/reward/find-reward",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                resource(
                                        ResourceSnippetParameters.builder()
                                                .tag("사장 모드")
                                                .description("고객의 리워드 조회")
                                                .requestHeaders(headerWithName("Authorization").description("임시(Basic)"))
                                                .queryParameters(parameterWithName("cafe-id").description("카페 Id"),
                                                        parameterWithName("used").description("false"))
                                                .responseFields(
                                                        fieldWithPath("rewards[].id").description("리워드 ID"),
                                                        fieldWithPath("rewards[].name").description("리워드 이름")
                                                )
                                                .responseSchema(Schema.schema("RewardsFindResponse"))
                                                .build()
                                )
                        )
                )
                .andExpect(status().isOk());
    }
}
