package com.stampcrush.backend.acceptance.step;

import static io.restassured.http.ContentType.JSON;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

public class ManagerReissueTokenStep {

    public static ExtractableResponse<Response> Refresh_토큰으로_토큰_재발급_요청(final String refreshToken) {
        return RestAssured.given()
                .log().all()
                .cookie("REFRESH_TOKEN", refreshToken)
                .contentType(JSON)
                .when()
                .get("/api/admin/auth/reissue-token")
                .then()
                .log().all()
                .extract();
    }
}
