package com.stampcrush.backend.api.docs.manager.image;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.Schema;
import com.stampcrush.backend.api.docs.DocsControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;

import java.util.Optional;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ManagerImageCommandApiDocsControllerTest extends DocsControllerTest {

    @Test
    void 이미지_업로드() throws Exception {
        // given
        MockMultipartFile multipartFile = new MockMultipartFile(
                "image", "image.jpg", MediaType.IMAGE_JPEG.toString(), "image data".getBytes()
        );
        when(ownerRepository.findById(OWNER.getId())).thenReturn(Optional.of(OWNER));
        when(managerImageCommandService.uploadImageAndReturnUrl(multipartFile)).thenReturn("imageUrl");
        when(authTokensGenerator.isValidToken(anyString())).thenReturn(true);
        when(authTokensGenerator.extractMemberId(anyString())).thenReturn(OWNER.getId());

        // when, then
        mockMvc.perform(RestDocumentationRequestBuilders.multipart("/api/admin/images")
                        .file(multipartFile)
                        .header(HttpHeaders.AUTHORIZATION, OWNER_BEARER_HEADER))

                .andDo(document("manager/image/upload",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                resource(
                                        ResourceSnippetParameters.builder()
                                                .tag("사장 모드")
                                                .description("이미지 업로드")
                                                .requestHeaders(headerWithName("Authorization").description("Bearer"))
                                                .responseFields(
                                                        fieldWithPath("imageUrl").description("업로드한 이미지의 Url")
                                                )
                                                .responseSchema(Schema.schema("ImageUrlResponse"))
                                                .build()
                                )
                        )
                )
                .andExpect(status().isOk());
    }
}
