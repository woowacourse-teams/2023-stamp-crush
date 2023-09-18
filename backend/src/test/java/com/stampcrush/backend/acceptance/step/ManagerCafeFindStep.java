package com.stampcrush.backend.acceptance.step;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

import static io.restassured.http.ContentType.JSON;

public class ManagerCafeFindStep {

    public static ExtractableResponse<Response> 자신의_카페_조회_요청(String accessToken) {
        return RestAssured.given()
                .log().all()
                .contentType(JSON)
                .auth().preemptive()
                .oauth2(accessToken)

                .when()
                .get("/api/admin/cafes")

                .then()
                .log().all()
                .extract();
    }
}
