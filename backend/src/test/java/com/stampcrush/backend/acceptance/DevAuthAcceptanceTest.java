package com.stampcrush.backend.acceptance;


import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

import com.stampcrush.backend.auth.api.response.AuthTokensResponse;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

public class DevAuthAcceptanceTest extends AcceptanceTest {

    @Test
    void 개발_환경에서_카페_사장으로_로그인한다() {
        // given
        ExtractableResponse<Response> 응답 = given().log().all()
                .get("/api/dev/login/admin/{joinedName}", "mallang")
                .then().log().all()
                .extract();

        // when
        AuthTokensResponse authTokensResponse = 응답.response().as(AuthTokensResponse.class);

        // then
        assertThat(authTokensResponse.getAccessToken()).isNotNull();
        assertThat(authTokensResponse.getRefreshToken()).isNotNull();
    }

    @Test
    void 개발_환경에서_손님으로_로그인한다() {
        // given
        ExtractableResponse<Response> 응답 = given().log().all()
                .get("/api/dev/login/visitor/{joinedName}", "mallang")
                .then().log().all()
                .extract();

        // when
        AuthTokensResponse authTokensResponse = 응답.response().as(AuthTokensResponse.class);

        // then
        assertThat(authTokensResponse.getAccessToken()).isNotNull();
        assertThat(authTokensResponse.getRefreshToken()).isNotNull();
    }
}
