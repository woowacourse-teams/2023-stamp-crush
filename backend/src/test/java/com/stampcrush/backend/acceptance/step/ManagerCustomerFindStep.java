package com.stampcrush.backend.acceptance.step;

import com.stampcrush.backend.entity.user.Owner;
import com.stampcrush.backend.helper.BearerAuthHelper;
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
                .oauth2(BearerAuthHelper.generateToken(owner.getId()))

                .when()
                .get("/api/admin/cafes/{cafeId}/customers", cafeId)

                .then()
                .log().all()
                .extract();
    }
}
