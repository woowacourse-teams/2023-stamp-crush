package com.stampcrush.backend.acceptance.step;

import com.stampcrush.backend.entity.user.Owner;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;

public class ManagerCustomerFindStep {

    public static ExtractableResponse<Response> 고객_목록_조회_요청(Owner owner, Long cafeId) {
        return given()
                .log().all()
                .contentType(JSON)
                .auth().preemptive()
                .basic(owner.getLoginId(), owner.getEncryptedPassword())

                .when()
                .get("/api/admin/cafes/{cafeId}/customers", cafeId)

                .then()
                .log().all()
                .extract();
    }
}
