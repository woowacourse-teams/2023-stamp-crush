package com.stampcrush.backend.api.docs.visitor.reward;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.Schema;
import com.stampcrush.backend.api.docs.DocsControllerTest;
import com.stampcrush.backend.application.visitor.reward.dto.VisitorRewardsFindResultDto;
import com.stampcrush.backend.entity.reward.Reward;
import com.stampcrush.backend.fixture.RewardFixture;
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

public class VisitorRewardsFindApiDocsControllerTest extends DocsControllerTest {

    @Test
    void 리워드_조회() throws Exception {
        // given, when
        when(customerRepository.findByLoginId(CUSTOMER.getLoginId())).thenReturn(Optional.of(CUSTOMER));
        Reward reward = RewardFixture.REWARD_USED_FALSE;

        when(visitorRewardsFindService.findRewards(null, false))
                .thenReturn(
                        List.of(
                                VisitorRewardsFindResultDto.from(reward)
                        )
                );

        // then
        mockMvc.perform(
                        RestDocumentationRequestBuilders.get("/api/rewards")
                                .param("used", "false")
                                .contentType(MediaType.APPLICATION_JSON)
                                .header(HttpHeaders.AUTHORIZATION, CUSTOMER_BASIC_HEADER))
                .andDo(document("visitor/rewards/find-rewards",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                resource(
                                        ResourceSnippetParameters.builder()
                                                .tag("고객 모드")
                                                .description("리워드 조회")
                                                .requestHeaders(headerWithName("Authorization").description("임시(Basic)"))
                                                .queryParameters(parameterWithName("used").description("false(사용 가능한 리워드), true(사용한 리워드)"))
                                                .responseFields(
                                                        fieldWithPath("rewards[].id").description("리워드 ID"),
                                                        fieldWithPath("rewards[].rewardName").description("리워드 이름"),
                                                        fieldWithPath("rewards[].cafeName").description("카페 이름"),
                                                        fieldWithPath("rewards[].createdAt").description("리워드 생성일"),
                                                        fieldWithPath("rewards[].usedAt").description("리워드 사용일")
                                                )
                                                .responseSchema(Schema.schema("VisitorRewardsFindResponse"))
                                                .build()
                                )
                        )
                )
                .andExpect(status().isOk());
    }
}
