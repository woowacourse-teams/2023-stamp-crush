package com.stampcrush.backend.acceptance.step;

import com.stampcrush.backend.entity.user.Owner;
import com.stampcrush.backend.helper.BearerAuthHelper;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

import static io.restassured.http.ContentType.JSON;

public class ManagerCafeFindStep {

    public static ExtractableResponse<Response> 오너의_모든_카페를_조회(Owner owner) {
        return RestAssured.given()
                .log().all()
                .contentType(JSON)
                .auth().preemptive()
                .oauth2(BearerAuthHelper.generateToken(owner.getId()))

                .when()
                .get("/api/admin/cafes")

                .then()
                .log().all()
                .extract();
    }
}
