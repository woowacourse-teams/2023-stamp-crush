package com.stampcrush.backend.acceptance.step;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;

public class VisitorRewardFindStep {

    public static ExtractableResponse<Response> 리워드_목록_조회(String accessToken) {
        return given()
                .log().all()
                .queryParam("used", false)
                .contentType(JSON)
                .auth().preemptive()
                .oauth2(accessToken)

                .when()
                .get("/api/rewards")
                .then()
                .log().all()
                .extract();
    }
}
