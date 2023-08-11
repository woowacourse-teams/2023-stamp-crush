package com.stampcrush.backend.acceptance.step;

import com.stampcrush.backend.entity.user.RegisterCustomer;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

public class VisitorCouponFindStep {

    public static ExtractableResponse<Response> 고객의_쿠폰_카페별로_1개씩_조회_요청(RegisterCustomer customer) {
        return RestAssured.given()
                .log().all()
                .auth().preemptive().basic(customer.getLoginId(), customer.getEncryptedPassword())

                .when()
                .get("/api/coupons")

                .then()
                .log().all()
                .extract();
    }
}
