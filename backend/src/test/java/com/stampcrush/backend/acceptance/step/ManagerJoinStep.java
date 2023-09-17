package com.stampcrush.backend.acceptance.step;

import com.stampcrush.backend.auth.OAuthProvider;
import com.stampcrush.backend.auth.api.request.OAuthRegisterOwnerCreateRequest;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;

public class ManagerJoinStep {

    public static final OAuthRegisterOwnerCreateRequest O_AUTH_OWNER_CREATE_REQUEST = new OAuthRegisterOwnerCreateRequest(
            "owner",
            OAuthProvider.KAKAO,
            938273L
    );

    public static final OAuthRegisterOwnerCreateRequest O_AUTH_OWNER_CREATE_REQUEST_2 = new OAuthRegisterOwnerCreateRequest(
            "notOwner",
            OAuthProvider.KAKAO,
            932862L
    );

    public static String 카페_사장_회원_가입_요청하고_액세스_토큰_반환(OAuthRegisterOwnerCreateRequest request) {
        ExtractableResponse<Response> response = 카페_사장_회원_가입_요청(request);
        return response.jsonPath().getString("accessToken");
    }

    public static ExtractableResponse<Response> 카페_사장_회원_가입_요청(OAuthRegisterOwnerCreateRequest request) {
        return given()
                .log().all()
                .contentType(JSON)
                .body(request)

                .when()
                .post("/api/admin/login/register/test/token")

                .then()
                .log().all()
                .extract();
    }
}
