package com.stampcrush.backend.acceptance.step;

import com.stampcrush.backend.api.cafe.request.CafeCreateRequest;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

import static io.restassured.http.ContentType.JSON;

public class CafeCreateStep {

    public static Long 카페_생성_요청하고_아이디_반환(Long ownerId, CafeCreateRequest cafeCreateRequest) {
        ExtractableResponse<Response> response = 카페_생성_요청(ownerId, cafeCreateRequest);
        String location = response.header("Location");
        System.out.println("location = " + location);
        return Long.valueOf(location.split("/")[2]);
    }

    public static ExtractableResponse<Response> 카페_생성_요청(Long ownerId, CafeCreateRequest cafeCreateRequest) {
        return RestAssured.given()
                .log().all()
                .contentType(JSON)
                .body(cafeCreateRequest)

                .when()
                .post("/api/cafes/" + ownerId)

                .then()
                .log().all()
                .extract();
    }
}
