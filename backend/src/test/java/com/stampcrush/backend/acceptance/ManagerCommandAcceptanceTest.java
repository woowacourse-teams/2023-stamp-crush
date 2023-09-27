package com.stampcrush.backend.acceptance;

import com.stampcrush.backend.api.manager.owner.request.OwnerCreateRequest;
import com.stampcrush.backend.api.manager.owner.request.OwnerLoginRequest;
import com.stampcrush.backend.auth.api.response.AuthTokensResponse;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static com.stampcrush.backend.acceptance.step.ManagerCreateStep.사장_생성_요청;
import static com.stampcrush.backend.acceptance.step.ManagerLoginStep.사장_로그인;
import static org.assertj.core.api.Assertions.assertThat;

public class ManagerCommandAcceptanceTest extends AcceptanceTest {

    @Test
    void 사장을_생성한다() {
        // given
        OwnerCreateRequest ownerCreateRequest = new OwnerCreateRequest("loginId", "password123");

        // when
        ExtractableResponse<Response> response = 사장_생성_요청(ownerCreateRequest);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    @Test
    void 사장이_로그인을_한다() {
        // given
        String loginId = "loginId";
        String password = "password123";
        OwnerCreateRequest ownerCreateRequest = new OwnerCreateRequest(loginId, password);
        사장_생성_요청(ownerCreateRequest);
        OwnerLoginRequest ownerLoginRequest = new OwnerLoginRequest(loginId, password);

        // when
        ExtractableResponse<Response> response = 사장_로그인(ownerLoginRequest);
        AuthTokensResponse responseBody = response.body().as(AuthTokensResponse.class);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(responseBody.getAccessToken()).isNotNull();
        assertThat(responseBody.getAccessToken()).isNotBlank();
    }
}
