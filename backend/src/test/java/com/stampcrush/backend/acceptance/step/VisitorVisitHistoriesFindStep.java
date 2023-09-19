package com.stampcrush.backend.acceptance.step;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

public class VisitorVisitHistoriesFindStep {

    public static ExtractableResponse<Response> 고객의_카페_방문_이력_조회(String accessToken) {
        return RestAssured.given()
                .log().all()
                .auth().preemptive()
                .oauth2(accessToken)

                .when()
                .get("/api/stamp-histories")

                .then()
                .log().all()
                .extract();
    }
}
