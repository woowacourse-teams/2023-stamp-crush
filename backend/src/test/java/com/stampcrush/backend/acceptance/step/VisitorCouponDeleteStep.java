package com.stampcrush.backend.acceptance.step;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

public class VisitorCouponDeleteStep {

    public static ExtractableResponse<Response> 쿠폰_삭제_요청(String customerAccessToken, Long gitchanCafeCouponId) {
        return RestAssured.given()
                .log().all()
                .auth().preemptive()
                .oauth2(customerAccessToken)

                .when()
                .delete("/api/coupons/" + gitchanCafeCouponId)

                .then()
                .log().all()
                .extract();
    }
}
