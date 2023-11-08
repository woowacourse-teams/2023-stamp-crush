package com.stampcrush.backend.acceptance;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static com.stampcrush.backend.acceptance.step.ManagerJoinStep.OWNER_CREATE_REQUEST;
import static com.stampcrush.backend.acceptance.step.ManagerJoinStep.카페_사장_회원_가입_요청;
import static com.stampcrush.backend.acceptance.step.ManagerJoinStep.카페_사장_회원_가입_요청하고_Refresh_토큰_반환;
import static com.stampcrush.backend.acceptance.step.ManagerLogoutStep.카페_사장_로그_아웃_요청;
import static com.stampcrush.backend.acceptance.step.ManagerReissueTokenStep.Refresh_토큰으로_토큰_재발급_요청;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

class ManagerReissueTokenAcceptanceTest extends AcceptanceTest {

    @Test
    void 카페_사장의_토큰을_재발급한다() {
        String refreshToken = 카페_사장_회원_가입_요청하고_Refresh_토큰_반환(OWNER_CREATE_REQUEST);
        final ExtractableResponse<Response> response = Refresh_토큰으로_토큰_재발급_요청(refreshToken);

        assertThat(response.statusCode()).isEqualTo(OK.value());
    }

    @Test
    void 조작된_Refresh_토큰으로는_토큰_재발급을_받을_수_없다() {
        String refreshToken = 카페_사장_회원_가입_요청하고_Refresh_토큰_반환(OWNER_CREATE_REQUEST);
        String invalidRefreshToken = "zozakHatZiRong" + refreshToken;

        final ExtractableResponse<Response> response = Refresh_토큰으로_토큰_재발급_요청(invalidRefreshToken);

        assertThat(response.statusCode()).isEqualTo(UNAUTHORIZED.value());
    }

    @Test
    void 로그아웃_이후에_해당_Refresh_Token을_사용해서_토큰_재발급을_받을_수_없다() {
        final ExtractableResponse<Response> authResponse = 카페_사장_회원_가입_요청(OWNER_CREATE_REQUEST);
        final String accessToken = authResponse.jsonPath().getString("accessToken");
        final String refreshToken = authResponse.jsonPath().getString("refreshToken");

        카페_사장_로그_아웃_요청(accessToken, refreshToken);

        final ExtractableResponse<Response> response = Refresh_토큰으로_토큰_재발급_요청(refreshToken);

        assertThat(response.statusCode()).isEqualTo(UNAUTHORIZED.value());
    }
}
