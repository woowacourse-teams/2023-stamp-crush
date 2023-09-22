package com.stampcrush.backend.acceptance.step;

import com.stampcrush.backend.helper.BearerAuthHelper;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;

public class VisitorRewardFindStep {

    public static ExtractableResponse<Response> 리워드_목록_조회(Long customerId) {
        return given()
                .log().all()
                .queryParam("used", false)
                .contentType(JSON)
                .auth().preemptive()
                .oauth2(BearerAuthHelper.generateToken(customerId))

                .when()
                .get("/api/rewards")
                .then()
                .log().all()
                .extract();
    }
}
