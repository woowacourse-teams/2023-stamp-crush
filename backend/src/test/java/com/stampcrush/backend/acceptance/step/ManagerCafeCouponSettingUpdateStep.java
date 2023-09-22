package com.stampcrush.backend.acceptance.step;

import com.stampcrush.backend.api.manager.cafe.request.CafeCouponSettingUpdateRequest;
import com.stampcrush.backend.entity.user.Owner;
import com.stampcrush.backend.helper.BearerAuthHelper;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

import java.util.List;

import static io.restassured.http.ContentType.JSON;

public class ManagerCafeCouponSettingUpdateStep {

    public static final CafeCouponSettingUpdateRequest CAFE_COUPON_SETTING_UPDATE_REQUEST = new CafeCouponSettingUpdateRequest(
            "frontImageUrl",
            "backImageUrl",
            "stampImageUrl",
            List.of(
                    new CafeCouponSettingUpdateRequest.CouponStampCoordinateRequest(1, 1, 1),
                    new CafeCouponSettingUpdateRequest.CouponStampCoordinateRequest(2, 2, 2)
            ),
            "reward",
            6
    );

    @Deprecated
    public static ExtractableResponse<Response> 카페_쿠폰_정책_수정_요청(CafeCouponSettingUpdateRequest request, Owner owner, Long cafeId) {
        return RestAssured.given()
                .log().all()
                .contentType(JSON)
                .auth().preemptive()
                .oauth2(BearerAuthHelper.generateToken(owner.getId()))
                .body(request)

                .when()
                .post("/api/admin/coupon-setting?cafe-id=" + cafeId)

                .then()
                .log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 카페_쿠폰_정책_수정_요청(CafeCouponSettingUpdateRequest request, String ownerAccessToken, Long cafeId) {
        return RestAssured.given()
                .log().all()
                .contentType(JSON)
                .auth().preemptive()
                .oauth2(ownerAccessToken)
                .body(request)

                .when()
                .post("/api/admin/coupon-setting?cafe-id=" + cafeId)

                .then()
                .log().all()
                .extract();
    }
}
