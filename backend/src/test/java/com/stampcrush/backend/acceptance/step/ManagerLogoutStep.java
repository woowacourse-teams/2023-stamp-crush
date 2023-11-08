package com.stampcrush.backend.acceptance.step;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

public class ManagerLogoutStep {

    public static ExtractableResponse<Response> 카페_사장_로그_아웃_요청(final String accessToken, final String refreshToken) {
        return RestAssured.given()
                .log().all()
                .auth().preemptive()
                .oauth2(accessToken)
                .header("Refresh", refreshToken)

                .when()
                .post("/api/admin/logout")

                .then()
                .log().all()
                .extract();
    }
}
