package com.stampcrush.backend.acceptance.step;

import com.stampcrush.backend.api.manager.coupon.response.CustomerAccumulatingCouponFindResponse;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

import java.util.List;

import static io.restassured.RestAssured.given;

public class ManagerCouponFindStep {

    public static List<CustomerAccumulatingCouponFindResponse> 고객의_쿠폰_조회하고_결과_반환(String accessToekn, Long cafeId, Long customerId) {
        ExtractableResponse<Response> response = 고객의_쿠폰_조회_요청(cafeId, accessToekn, customerId);
        return response.jsonPath()
                .getList("coupons", CustomerAccumulatingCouponFindResponse.class);
    }

    public static ExtractableResponse<Response> 고객의_쿠폰_조회_요청(Long cafeId, String accessToken, Long customerId) {
        return given()
                .log().all()
                .auth().preemptive()
                .oauth2(accessToken)

                .queryParam("cafe-id", cafeId)
                .queryParam("active", true)

                .when()
                .get("api/admin/customers/{customerId}/coupons", customerId)

                .then()
                .log().all()
                .extract();
    }
}
