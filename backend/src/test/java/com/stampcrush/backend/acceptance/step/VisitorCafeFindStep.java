package com.stampcrush.backend.acceptance.step;

import com.stampcrush.backend.entity.user.Customer;
import com.stampcrush.backend.helper.BearerAuthHelper;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.MediaType;

import static io.restassured.RestAssured.given;

public final class VisitorCafeFindStep {

    public static ExtractableResponse<Response> 고객의_카페_정보_조회_요청(Customer customer, Long cafeId) {
        return given()
                .log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .auth().preemptive()
                .oauth2(BearerAuthHelper.generateToken(customer.getId()))

//                .basic(customer.getLoginId(), customer.getEncryptedPassword())

                .when()
                .get("/api/cafes/" + cafeId)

                .then()
                .log().all()
                .extract();
    }
}
