package com.stampcrush.backend.acceptance.step;

import com.stampcrush.backend.entity.user.Owner;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

public class CouponFindStep {

    public static ExtractableResponse<Response> 고객의_쿠폰_카페별로_1개씩_조회_요청(Owner owner, Long customerId) {
        return RestAssured.given()
                .log().all()
                .auth().preemptive().basic(owner.getLoginId(), owner.getEncryptedPassword())

                .when()
                .get("/api/coupons/" + customerId)

                .then()
                .log().all()
                .extract();
    }
}
