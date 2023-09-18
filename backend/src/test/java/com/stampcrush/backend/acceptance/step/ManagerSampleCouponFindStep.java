package com.stampcrush.backend.acceptance.step;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

public class ManagerSampleCouponFindStep {

    public static ExtractableResponse<Response> 샘플_쿠폰_조회_요청(String accessToken, int maxStampCount) {
        return RestAssured.given()
                .log().all()
                .auth().preemptive()
                .oauth2(accessToken)

                .when()
                .get("/api/admin/coupon-samples?max-stamp-count=" + maxStampCount)

                .then()
                .extract();
    }
}
