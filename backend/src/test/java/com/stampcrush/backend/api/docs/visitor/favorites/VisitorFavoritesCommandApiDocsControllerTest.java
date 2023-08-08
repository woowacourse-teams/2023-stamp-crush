package com.stampcrush.backend.api.docs.visitor.favorites;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.Schema;
import com.stampcrush.backend.api.docs.DocsControllerTest;
import com.stampcrush.backend.api.visitor.favorites.request.FavoritesUpdateRequest;
import com.stampcrush.backend.entity.user.RegisterCustomer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;

import java.util.Base64;
import java.util.Optional;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static com.stampcrush.backend.fixture.CustomerFixture.REGISTER_CUSTOMER_GITCHAN;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class VisitorFavoritesCommandApiDocsControllerTest extends DocsControllerTest {

    private static final Long CAFE_ID = 1L;

    private RegisterCustomer customer;
    private String basicAuthHeader;

    @BeforeEach
    void setUp() {
        customer = REGISTER_CUSTOMER_GITCHAN;

        String username = customer.getLoginId();
        String password = customer.getEncryptedPassword();
        basicAuthHeader = "Basic " + Base64.getEncoder().encodeToString((username + ":" + password).getBytes());
    }

    @Test
    void 즐겨찾기_등록_해제_요청() throws Exception {
        // given
        when(customerRepository.findByLoginId(customer.getLoginId())).thenReturn(Optional.of(customer));
        FavoritesUpdateRequest request = new FavoritesUpdateRequest(true);

        // when, then
        mockMvc.perform(
                        RestDocumentationRequestBuilders.post("/api/cafes/{cafeId}/favorites", CAFE_ID)
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON)
                                .header(HttpHeaders.AUTHORIZATION, basicAuthHeader))
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
