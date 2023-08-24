package com.stampcrush.backend.acceptance.step;

import com.stampcrush.backend.auth.request.OAuthRegisterCustomerCreateRequest;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class VisitorJoinStep {

    public static String 회원_가입_요청하고_액세스_토큰_반환(OAuthRegisterCustomerCreateRequest request) {
        ExtractableResponse<Response> response = 회원_가입_요청(request);
        return response.jsonPath().getString("accessToken");
    }

    public static ExtractableResponse<Response> 회원_가입_요청(OAuthRegisterCustomerCreateRequest request) {
        return given()
                .log().all()
                .body(request)

                .when()
                .post("/api/login/test/token")

                .then()
                .log().all()
                .extract();
    }
}
