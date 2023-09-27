package com.stampcrush.backend.acceptance.step;

import com.stampcrush.backend.api.manager.owner.request.OwnerCreateRequest;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

import static io.restassured.http.ContentType.JSON;

public class ManagerCreateStep {

    public static ExtractableResponse<Response> 사장_생성_요청(OwnerCreateRequest request) {
        return RestAssured.given()
                .log().all()
                .contentType(JSON)
                .body(request)

                .when()
                .post("/api/admin/owners")

                .then()
                .log().all()
                .extract();
    }
}
