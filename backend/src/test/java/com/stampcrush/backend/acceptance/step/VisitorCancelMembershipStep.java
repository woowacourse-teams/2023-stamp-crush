package com.stampcrush.backend.acceptance.step;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

import static io.restassured.http.ContentType.JSON;

public class VisitorCancelMembershipStep {

    public static ExtractableResponse<Response> 가입_고객_회원_탈퇴_요청(String accessToken) {
        return RestAssured.given()
                .log().all()
                .contentType(JSON)
                .auth().preemptive()
                .oauth2(accessToken)

                .when()
                .delete("/api/customers")

                .then()
                .log().all()
                .extract();
    }
}
