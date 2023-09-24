package com.stampcrush.backend.acceptance.step;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

import static io.restassured.http.ContentType.JSON;

public class ManagerCafeCouponSettingFindStep {

    public static ExtractableResponse<Response> 쿠폰이_발급될때의_쿠폰_디자인_조회_요청(String accessToken, Long cafeId, Long couponId) {
        return RestAssured.given()
                .log().all()
                .contentType(JSON)
                .auth().preemptive()
                .oauth2(accessToken)

                .when()
                .get("/api/admin/coupon-setting/" + couponId + "?cafe-id=" + cafeId)

                .then()
                .log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 카페의_현재_쿠폰_디자인_정책_조회_요청(String accessToken, Long cafeId) {
        return RestAssured.given()
                .log().all()
                .contentType(JSON)
                .auth().preemptive()
                .oauth2(accessToken)

                .when()
                .get("/api/admin/coupon-setting?cafe-id=" + cafeId)

                .then()
                .log().all()
                .extract();
    }
}
