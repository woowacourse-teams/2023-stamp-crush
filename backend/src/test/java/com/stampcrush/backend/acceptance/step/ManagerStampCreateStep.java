package com.stampcrush.backend.acceptance.step;

import com.stampcrush.backend.api.manager.coupon.request.StampCreateRequest;
import com.stampcrush.backend.entity.user.Customer;
import com.stampcrush.backend.entity.user.Owner;
import com.stampcrush.backend.helper.BearerAuthHelper;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;

public class ManagerStampCreateStep {

    public static ExtractableResponse<Response> 쿠폰에_스탬프를_적립_요청(String accessToken, Customer customer, Long couponId, StampCreateRequest stampCreateRequest) {
        return given()
                .log().all()
                .body(stampCreateRequest)
                .contentType(JSON)
                .auth().preemptive()
                .oauth2(accessToken)

                .when()
                .post("/api/admin/customers/{customerId}/coupons/{couponId}/stamps", customer.getId(), couponId)

                .then()
                .log().all()
                .extract();
    }
}
