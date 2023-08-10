package com.stampcrush.backend.acceptance.step;

import com.stampcrush.backend.api.manager.coupon.request.CouponCreateRequest;
import com.stampcrush.backend.api.manager.coupon.response.CouponCreateResponse;
import com.stampcrush.backend.entity.user.Owner;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

import static io.restassured.http.ContentType.JSON;

public class ManagerCouponCreateStep {

    public static Long 쿠폰_생성_요청하고_아이디_반환(Owner owner, CouponCreateRequest request, Long customerId) {
        ExtractableResponse<Response> response = 쿠폰_생성_요청(owner, request, customerId);
        CouponCreateResponse couponCreateResponse = response.body().as(CouponCreateResponse.class);
        return couponCreateResponse.getCouponId();
    }

    public static ExtractableResponse<Response> 쿠폰_생성_요청(Owner owner, CouponCreateRequest request, Long customerId) {
        return RestAssured.given()
                .log().all()
                .contentType(JSON)
                .body(request)
                .auth().preemptive().basic(owner.getLoginId(), owner.getEncryptedPassword())

                .when()
                .post("/api/admin/customers/" + customerId + "/coupons")

                .then()
                .log().all()
                .extract();
    }
}
