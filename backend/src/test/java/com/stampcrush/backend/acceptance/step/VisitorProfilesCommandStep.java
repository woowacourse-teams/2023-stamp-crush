package com.stampcrush.backend.acceptance.step;

import com.stampcrush.backend.api.visitor.profile.request.PhoneNumberUpdateRequest;
import com.stampcrush.backend.entity.user.RegisterCustomer;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;

public final class VisitorProfilesCommandStep {

    public static ExtractableResponse<Response> 고객의_전화번호_등록_요청(
            RegisterCustomer customer,
            PhoneNumberUpdateRequest request
    ) {
        return given()
                .log().all()
                .contentType(JSON)
                .body(request)
                .auth().preemptive()
                .basic(customer.getLoginId(), customer.getEncryptedPassword())

                .when()
                .post("/api/profiles/phone-number")

                .then()
                .extract();
    }
}
