package com.stampcrush.backend.api.docs.manager.cafe;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.Schema;
import com.stampcrush.backend.api.docs.DocsControllerTest;
import com.stampcrush.backend.api.manager.cafe.request.CafeUpdateRequest;
import com.stampcrush.backend.entity.user.Owner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;

import java.time.LocalTime;
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

public class ManagerCafeCommandApiDocsControllerTest extends DocsControllerTest {

    private static final Long CAFE_ID = 1L;

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
    void 카페_상세_정보_변경() throws Exception {
        // given
        when(ownerRepository.findByLoginId(owner.getLoginId())).thenReturn(Optional.of(owner));
        CafeUpdateRequest request = new CafeUpdateRequest("안녕하세요", LocalTime.NOON, LocalTime.MIDNIGHT, "01012345678", "imageUrl");

        // when, then
        mockMvc.perform(RestDocumentationRequestBuilders.patch("/api/admin/cafes/{cafeId}", CAFE_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .header(HttpHeaders.AUTHORIZATION, basicAuthHeader))
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
}
