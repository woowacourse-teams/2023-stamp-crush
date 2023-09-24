package com.stampcrush.backend.acceptance.step;

import com.stampcrush.backend.entity.user.Customer;
import com.stampcrush.backend.helper.BearerAuthHelper;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

public class VisitorCouponFindStep {

    public static ExtractableResponse<Response> 고객의_쿠폰_카페별로_1개씩_조회_요청(String accessToken) {
        return RestAssured.given()
                .log().all()
                .auth().preemptive()
                .oauth2(accessToken)

                .when()
                .get("/api/coupons")

                .then()
                .log().all()
                .extract();
    }
}
