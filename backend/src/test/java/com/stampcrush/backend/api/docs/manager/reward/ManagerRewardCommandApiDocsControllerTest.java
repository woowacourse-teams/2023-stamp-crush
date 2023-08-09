package com.stampcrush.backend.api.docs.manager.reward;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.Schema;
import com.stampcrush.backend.api.docs.DocsControllerTest;
import com.stampcrush.backend.api.manager.reward.request.RewardUsedUpdateRequest;
import com.stampcrush.backend.entity.user.Owner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;

import java.util.Base64;
import java.util.Optional;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static com.stampcrush.backend.fixture.OwnerFixture.OWNER3;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ManagerRewardCommandApiDocsControllerTest extends DocsControllerTest {

    private Owner owner;
    private String basicAuthHeader;

    @BeforeEach
    void setUp() {
        owner = OWNER3;

        String username = owner.getLoginId();
        String password = owner.getEncryptedPassword();
        basicAuthHeader = "Basic " + Base64.getEncoder().encodeToString((username + ":" + password).getBytes());
    }

    @Test
    void 리워드_사용() throws Exception {
        // given
        Long cafeId = 1L;
        Long rewardId = 1L;
        when(ownerRepository.findByLoginId(owner.getLoginId())).thenReturn(Optional.of(owner));
        RewardUsedUpdateRequest request = new RewardUsedUpdateRequest(cafeId, true);

        // when, then
        mockMvc.perform(RestDocumentationRequestBuilders.patch("/api/admin/customers/{customerId}/rewards/{rewardId}", cafeId, rewardId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, basicAuthHeader)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(document("manager/reward/use-reward",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                resource(
                                        ResourceSnippetParameters.builder()
                                                .tag("사장 모드")
                                                .description("리워드 사용")
                                                .requestHeaders(headerWithName("Authorization").description("임시(Basic)"))
                                                .requestFields(fieldWithPath("cafeId").description("카페 Id"),
                                                        fieldWithPath("used").description("사용(true)"))
                                                .requestSchema(Schema.schema("RewardUsedUpdateRequest"))
                                                .build()
                                )
                        )
                )
                .andExpect(status().isOk());
    }
}
