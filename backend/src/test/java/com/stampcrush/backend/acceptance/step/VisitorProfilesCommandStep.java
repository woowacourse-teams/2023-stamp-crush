package com.stampcrush.backend.acceptance.step;

import com.stampcrush.backend.api.visitor.profile.request.VisitorProfilesPhoneNumberUpdateRequest;
import com.stampcrush.backend.entity.user.Customer;
import com.stampcrush.backend.helper.BearerAuthHelper;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;

public final class VisitorProfilesCommandStep {

    public static ExtractableResponse<Response> 고객의_전화번호_등록_요청(
            Customer customer,
            VisitorProfilesPhoneNumberUpdateRequest request
    ) {
        return given()
                .log().all()
                .contentType(JSON)
                .body(request)
                .auth().preemptive()
                .oauth2(BearerAuthHelper.generateToken(customer.getId()))

                .when()
                .post("/api/profiles/phone-number")

                .then()
                .extract();
    }

    public static ExtractableResponse<Response> 고객의_전화번호_등록_요청_token(
            String accessToken,
            VisitorProfilesPhoneNumberUpdateRequest request
    ) {
        return given()
                .log().all()
                .contentType(JSON)
                .body(request)
                .auth().preemptive()
                .oauth2(accessToken)

                .when()
                .post("/api/profiles/phone-number")

                .then()
                .extract();
    }
}
