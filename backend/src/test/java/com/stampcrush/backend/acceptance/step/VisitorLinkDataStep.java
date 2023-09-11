package com.stampcrush.backend.acceptance.step;

import com.stampcrush.backend.api.visitor.profile.VisitorProfilesLinkDataRequest;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

import static io.restassured.http.ContentType.JSON;

public class VisitorLinkDataStep {

    public static ExtractableResponse<Response> 회원_데이터_연동_요청(String accessToken, VisitorProfilesLinkDataRequest request) {
        return RestAssured.given()
                .log().all()
                .contentType(JSON)
                .auth().preemptive()
                .oauth2(accessToken)
                .body(request)

                .when()
                .post("/api/profiles/link-data")

                .then()
                .log().all()
                .extract();
    }
}

