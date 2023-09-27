package com.stampcrush.backend.acceptance.step;

import com.stampcrush.backend.api.manager.owner.request.OwnerCreateRequest;
import com.stampcrush.backend.api.manager.owner.request.OwnerLoginRequest;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

import static io.restassured.http.ContentType.JSON;

public class ManagerLoginStep {

    public static ExtractableResponse<Response> 사장_로그인(OwnerLoginRequest request) {
        return RestAssured.given()
                .log().all()
                .contentType(JSON)
                .body(request)

                .when()
                .post("/api/admin/login")

                .then()
                .log().all()
                .extract();
    }
}
