package com.stampcrush.backend.acceptance.step;

import com.stampcrush.backend.api.manager.coupon.response.CustomerAccumulatingCouponFindResponse;
import com.stampcrush.backend.entity.user.RegisterCustomer;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

import java.util.List;

import static io.restassured.RestAssured.given;

public class ManagerCouponFindStep {

    public static List<CustomerAccumulatingCouponFindResponse> 고객의_쿠폰_조회하고_결과_반환(Long savedCafeId, RegisterCustomer savedCustomer) {
        ExtractableResponse<Response> response = 고객의_쿠폰_조회_요청(savedCafeId, savedCustomer);
        return response.jsonPath()
                .getList("coupons", CustomerAccumulatingCouponFindResponse.class);
    }

    public static ExtractableResponse<Response> 고객의_쿠폰_조회_요청(Long savedCafeId, RegisterCustomer savedCustomer) {
        return given()
                .log().all()
                .auth().preemptive()
                .basic(savedCustomer.getLoginId(), savedCustomer.getEncryptedPassword())
                .queryParam("cafe-id", savedCafeId)
                .queryParam("active", true)

                .when()
                .get("api/admin/customers/{customerId}/coupons", savedCustomer.getId())

                .then()
                .log().all()
                .extract();
    }
}
