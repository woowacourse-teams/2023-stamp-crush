package com.stampcrush.backend.api.docs.visitor.favorites;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.Schema;
import com.stampcrush.backend.api.docs.DocsControllerTest;
import com.stampcrush.backend.api.visitor.favorites.request.FavoritesUpdateRequest;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
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

public class VisitorFavoritesCommandApiDocsControllerTest extends DocsControllerTest {

    @Test
    void 즐겨찾기_등록_해제_요청() throws Exception {
        // given
        when(customerRepository.findById(CUSTOMER.getId())).thenReturn(Optional.of(CUSTOMER));
        when(customerRepository.findByLoginId(CUSTOMER.getLoginId())).thenReturn(Optional.of(CUSTOMER));
        FavoritesUpdateRequest request = new FavoritesUpdateRequest(true);
        when(authTokensGenerator.isValidToken(anyString())).thenReturn(true);
        when(authTokensGenerator.extractMemberId(anyString())).thenReturn(CUSTOMER.getId());
        // when, then
        mockMvc.perform(
                        RestDocumentationRequestBuilders.post("/api/cafes/{cafeId}/favorites", CAFE_ID)
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON)
                                .header(HttpHeaders.AUTHORIZATION, CUSTOMER_BEARER_HEADER))
                .andDo(document("visitor/favorites/register-favorites",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                resource(
                                        ResourceSnippetParameters.builder()
                                                .tag("고객 모드")
                                                .description("카페 즐겨찾기 등록/해제")
                                                .requestHeaders(headerWithName("Authorization").description("임시(Basic)"))
                                                .requestFields(fieldWithPath("isFavorites").description("등록(true)/ 해제(false)"))
                                                .requestSchema(Schema.schema("FavoritesUpdateRequest"))
                                                .build()
                                )
                        )
                )
                .andExpect(status().isOk());
    }
}
