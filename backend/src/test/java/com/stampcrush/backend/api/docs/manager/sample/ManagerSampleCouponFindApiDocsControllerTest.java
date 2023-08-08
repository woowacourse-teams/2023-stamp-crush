package com.stampcrush.backend.api.docs.manager.sample;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.Schema;
import com.stampcrush.backend.api.docs.DocsControllerTest;
import com.stampcrush.backend.application.manager.sample.dto.SampleCouponsFindResultDto;
import com.stampcrush.backend.entity.user.Owner;
import com.stampcrush.backend.fixture.SampleCouponFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;

import java.util.Base64;
import java.util.List;
import java.util.Optional;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static com.stampcrush.backend.fixture.OwnerFixture.OWNER3;
import static com.stampcrush.backend.fixture.SampleCouponFixture.*;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ManagerSampleCouponFindApiDocsControllerTest extends DocsControllerTest {

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
    void 스탬프_개수별로_기본_샘플_조회() throws Exception {
        // given
        int maxStampCount = 8;
        when(ownerRepository.findByLoginId(owner.getLoginId())).thenReturn(Optional.of(owner));
        when(managerSampleCouponFindService.findSampleCouponsByMaxStampCount(maxStampCount))
                .thenReturn(
                        SampleCouponsFindResultDto.of(
                                List.of(SAMPLE_FRONT_IMAGE_SAVED),
                                List.of(SAMPLE_BACK_IMAGE_SAVED),
                                SampleCouponFixture.SAMPLE_COORDINATES_SIZE_EIGHT,
                                List.of(SAMPLE_STAMP_IMAGE_SAVED)
                        )
                );

        mockMvc.perform(
                        RestDocumentationRequestBuilders.get("/api/admin/coupon-samples?max-stamp-count=" + maxStampCount)
                                .contentType(APPLICATION_JSON)
                                .header(AUTHORIZATION, basicAuthHeader)
                )
                .andDo(document("manager/samples/find-samples",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                resource(
                                        ResourceSnippetParameters.builder()
                                                .tag("사장 모드")
                                                .description("스탬프 개수별로 개본 샘플 조회")
                                                .requestHeaders(headerWithName("Authorization").description("임시(Basic)"))
                                                .queryParameters(parameterWithName("max-stamp-count").description("스탬프 개수(8, 10, 12)"))
                                                .responseFields(
                                                        fieldWithPath("sampleFrontImages[].id").description("쿠폰 앞면 이미지 ID"),
                                                        fieldWithPath("sampleFrontImages[].imageUrl").description("쿠폰 앞면 이미지 URL"),
                                                        fieldWithPath("sampleBackImages[].id").description("쿠폰 뒷면 이미지 ID"),
                                                        fieldWithPath("sampleBackImages[].imageUrl").description("쿠폰 뒷면 이미지 URL"),
                                                        fieldWithPath("sampleBackImages[].stampCoordinates[].order").description("스탬프 좌표 순서"),
                                                        fieldWithPath("sampleBackImages[].stampCoordinates[].xCoordinate").description("스탬프 X 좌표"),
                                                        fieldWithPath("sampleBackImages[].stampCoordinates[].yCoordinate").description("스탬프 Y 좌표"),
                                                        fieldWithPath("sampleStampImages[].id").description("스탬프 이미지 ID"),
                                                        fieldWithPath("sampleStampImages[].imageUrl").description("스탬프 이미지 URL")
                                                )
                                                .responseSchema(Schema.schema("SampleCouponFindResponse"))
                                                .build()
                                )
                        )
                )
                .andExpect(status().isOk());
    }
}
