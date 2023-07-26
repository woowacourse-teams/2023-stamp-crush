package com.stampcrush.backend.acceptance.step;

import com.stampcrush.backend.api.customer.request.TemporaryCustomerCreateRequest;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

import static io.restassured.http.ContentType.JSON;

public class TemporaryCustomerCreateStep {

    public static Long 전화번호로_임시_고객_등록_요청하고_아이디_반환(TemporaryCustomerCreateRequest request) {
        ExtractableResponse<Response> response = 전화번호로_임시_고객_등록_요청(request);
        return Long.valueOf(response.header("Location").split("/")[2]);
    }

    public static ExtractableResponse<Response> 전화번호로_임시_고객_등록_요청(TemporaryCustomerCreateRequest request) {
        return RestAssured.given()
                .log().all()
                .contentType(JSON)
                .body(request)

                .when()
                .post("/api/temporary-customers")

                .then()
                .log().all()
                .extract();
    }
}
