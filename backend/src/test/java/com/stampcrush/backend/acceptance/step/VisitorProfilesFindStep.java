package com.stampcrush.backend.acceptance.step;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;

public class VisitorProfilesFindStep {

    public static ExtractableResponse<Response> 고객의_프로필_조회_요청(String accessToken) {
        return given()
                .log().all()
                .contentType(JSON)
                .auth().preemptive()
                .oauth2(accessToken)

                .when()
                .get("/api/profiles")

                .then()
                .extract();
    }

    public static ExtractableResponse<Response> 전화번호로_고객의_프로필_조회_요청(String accessToken, String phoneNumber) {
        return given()
                .log().all()
                .auth().preemptive()
                .oauth2(accessToken)

                .when()
                .get("/api/profiles/search?phone-number=" + phoneNumber)

                .then()
                .log().all()
                .extract();
    }
}
