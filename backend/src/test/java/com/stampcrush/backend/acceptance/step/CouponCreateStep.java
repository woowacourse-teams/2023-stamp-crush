package com.stampcrush.backend.acceptance.step;

import com.stampcrush.backend.api.coupon.request.CouponCreateRequest;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

import static io.restassured.http.ContentType.JSON;

public class CouponCreateStep {

    public static Long 쿠폰_생성_요청하고_아이디_반환(CouponCreateRequest request, Long customerId) {
        ExtractableResponse<Response> response = 쿠폰_생성_요청(request, customerId);
        return response.jsonPath().getLong("couponId");
    }

    public static ExtractableResponse<Response> 쿠폰_생성_요청(CouponCreateRequest request, Long customerId) {
        return RestAssured.given()
                .log().all()
                .contentType(JSON)
                .body(request)

                .when()
                .post("/api/customers/" + customerId + "/coupons")

                .then()
                .log().all()
                .extract();
    }
}
