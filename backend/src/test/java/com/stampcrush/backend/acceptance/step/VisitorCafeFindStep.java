package com.stampcrush.backend.acceptance.step;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.MediaType;

import static io.restassured.RestAssured.given;

public final class VisitorCafeFindStep {

    public static ExtractableResponse<Response> 고객의_카페_정보_조회_요청(String accessToken, Long cafeId) {
        return given()
                .log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .auth().preemptive()
                .oauth2(accessToken)

                .when()
                .get("/api/cafes/" + cafeId)

                .then()
                .log().all()
                .extract();
    }
}
