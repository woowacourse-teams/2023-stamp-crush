package com.stampcrush.backend.acceptance.step;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.MediaType;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;

public class ManagerCustomerFindStep {

    public static ExtractableResponse<Response> 고객_목록_조회_요청(String accessToken, Long cafeId) {
        return given()
                .log().all()
                .contentType(JSON)
                .auth().preemptive()
                .oauth2(accessToken)

                .when()
                .get("/api/admin/cafes/{cafeId}/customers", cafeId)

                .then()
                .log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 전화번호로_고객_조회_요청(String accessToken, String phoneNumber) {
        return RestAssured.given()
                .log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .param("phone-number", phoneNumber)
                .auth().preemptive()
                .oauth2(accessToken)

                .when()
                .get("/api/admin/customers")

                .then()
                .log().all()
                .extract();
    }
}
