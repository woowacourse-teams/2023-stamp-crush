package com.stampcrush.backend.acceptance.step;

import com.stampcrush.backend.api.manager.cafe.request.CafeUpdateRequest;
import com.stampcrush.backend.entity.user.Owner;
import com.stampcrush.backend.helper.BearerAuthHelper;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

import java.time.LocalTime;

import static io.restassured.http.ContentType.JSON;

public class ManagerCafeUpdateStep {

    public static final CafeUpdateRequest cafeUpdateRequest = new CafeUpdateRequest("hi", LocalTime.MIDNIGHT, LocalTime.NOON, "010123421253", "cafeimage");

    public static ExtractableResponse<Response> 카페_정보_업데이트_요청(Owner owner, CafeUpdateRequest cafeUpdateRequest, Long cafeId) {
        return RestAssured.given()
                .log().all()
                .contentType(JSON)
                .body(cafeUpdateRequest)
                .auth().preemptive()
                .oauth2(BearerAuthHelper.generateToken(owner.getId()))

//                .basic(owner.getLoginId(), owner.getEncryptedPassword())

                .when()
                .patch("/api/admin/cafes/" + cafeId)

                .then()
                .log().all()
                .extract();
    }
}
