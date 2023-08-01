package com.stampcrush.backend.acceptance.step;

import com.stampcrush.backend.api.manager.cafe.request.CafeCreateRequest;
import com.stampcrush.backend.entity.user.Owner;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

import static io.restassured.http.ContentType.JSON;

public class CafeCreateStep {

    public static final CafeCreateRequest CAFE_CREATE_REQUEST = new CafeCreateRequest("깃짱카페", "서초구", "우리집", "01010101010");

    public static Long 카페_생성_요청하고_아이디_반환(Owner owner, Long ownerId, CafeCreateRequest cafeCreateRequest) {
        ExtractableResponse<Response> response = 카페_생성_요청(owner, ownerId, cafeCreateRequest);
        String location = response.header("Location");
        System.out.println("location = " + location);
        return Long.valueOf(location.split("/")[2]);
    }

    public static ExtractableResponse<Response> 카페_생성_요청(Owner owner, Long ownerId, CafeCreateRequest cafeCreateRequest) {
        return RestAssured.given()
                .log().all()
                .contentType(JSON)
                .body(cafeCreateRequest)
                .auth().preemptive().basic(owner.getLoginId(), owner.getEncryptedPassword())

                .when()
                .post("/api/admin/cafes/" + ownerId)

                .then()
                .log().all()
                .extract();
    }
}
