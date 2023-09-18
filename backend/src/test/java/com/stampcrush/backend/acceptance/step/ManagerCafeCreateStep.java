package com.stampcrush.backend.acceptance.step;

import com.stampcrush.backend.api.manager.cafe.request.CafeCreateRequest;
import com.stampcrush.backend.entity.user.Owner;
import com.stampcrush.backend.helper.BearerAuthHelper;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

import static io.restassured.http.ContentType.JSON;

public class ManagerCafeCreateStep {

    public static final CafeCreateRequest CAFE_CREATE_REQUEST = new CafeCreateRequest("깃짱카페", "서초구", "우리집", "01010101010");

    @Deprecated
    public static Long 카페_생성_요청하고_아이디_반환(Owner owner, CafeCreateRequest cafeCreateRequest) {
        ExtractableResponse<Response> response = 카페_생성_요청(owner, cafeCreateRequest);
        String location = response.header("Location");
        return Long.valueOf(location.split("/")[2]);
    }

    @Deprecated
    public static ExtractableResponse<Response> 카페_생성_요청(Owner owner, CafeCreateRequest cafeCreateRequest) {
        return RestAssured.given()
                .log().all()
                .contentType(JSON)
                .body(cafeCreateRequest)
                .auth().preemptive()
                .oauth2(BearerAuthHelper.generateToken(owner.getId()))

                .when()
                .post("/api/admin/cafes")

                .then()
                .log().all()
                .extract();
    }

    public static Long 카페_생성_요청하고_아이디_반환_2(String accessToken, CafeCreateRequest cafeCreateRequest) {
        ExtractableResponse<Response> response = 카페_생성_요청_2(accessToken, cafeCreateRequest);
        String location = response.header("Location");
        return Long.valueOf(location.split("/")[2]);
    }

    public static ExtractableResponse<Response> 카페_생성_요청_2(String accessToken, CafeCreateRequest cafeCreateRequest) {
        return RestAssured.given()
                .log().all()
                .contentType(JSON)
                .body(cafeCreateRequest)
                .auth().preemptive()
                .oauth2(accessToken)

                .when()
                .post("/api/admin/cafes")

                .then()
                .log().all()
                .extract();
    }
}
