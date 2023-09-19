package com.stampcrush.backend.acceptance.step;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

import static io.restassured.http.ContentType.JSON;

public class VisitorCouponDeleteStep {

    public static ExtractableResponse<Response> 쿠폰_삭제_요청(String customerAccessToken, Long couponId) {
        return RestAssured.given()
                .log().all()
                .contentType(JSON)
                .auth().preemptive()
                .oauth2(customerAccessToken)

                .when()
                .delete("/api/coupons/{couponId}", couponId)

                .then()
                .log().all()
                .extract();
    }
}
