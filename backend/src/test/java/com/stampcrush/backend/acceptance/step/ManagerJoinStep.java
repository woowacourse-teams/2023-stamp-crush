package com.stampcrush.backend.acceptance.step;

import com.stampcrush.backend.auth.api.request.OAuthRegisterOwnerCreateRequest;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;

public class ManagerJoinStep {

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
