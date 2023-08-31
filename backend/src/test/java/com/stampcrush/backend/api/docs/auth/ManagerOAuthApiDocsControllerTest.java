package com.stampcrush.backend.api.docs.auth;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.Schema;
import com.stampcrush.backend.api.docs.DocsControllerTest;
import com.stampcrush.backend.auth.api.response.AuthTokensResponse;
import com.stampcrush.backend.auth.application.util.KakaoLoginParams;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ManagerOAuthApiDocsControllerTest extends DocsControllerTest {

    // TODO: 현재 ManagerOAuthApiController 가 테스트 프로파일에서 로딩되지 않게 설정해둬서 Disable. TestConfig 분리 후 enable 필요.
    @Test
    @Disabled
    void 로그인_요청() throws Exception {
        // given
        when(managerOAuthService.findLoginRedirectUri()).thenReturn("redirectUrl");

        // when, then
        mockMvc.perform(RestDocumentationRequestBuilders.get("/api/admin/login/kakao"))
                .andDo(document("auth/manage/login",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                resource(
                                        ResourceSnippetParameters.builder()
                                                .tag("사장 모드")
                                                .description("로그인 요청")
                                                .build()
                                )
                        )
                )
                .andExpect(status().isFound());
    }
}
