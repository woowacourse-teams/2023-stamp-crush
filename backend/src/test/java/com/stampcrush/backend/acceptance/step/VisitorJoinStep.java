package com.stampcrush.backend.acceptance.step;

import com.stampcrush.backend.auth.request.OAuthRegisterCustomerCreateRequest;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;

public class VisitorJoinStep {

    public static Long 임시_회원_가입_요청하고_아이디_반환(String phoneNumber) {
        ExtractableResponse<Response> response = 임시_회원_가입_요청(phoneNumber);
        String location = response.header("Location");
        return Long.valueOf(location.split("/")[2]);
    }

    public static ExtractableResponse<Response> 임시_회원_가입_요청(String phoneNumber) {
        return RestAssured.given()
                .log().all()
                .contentType(JSON)

                .when()
                .post("/api/login/customer/temporary/test?phone-number=" + phoneNumber)

                .then()
                .log().all()
                .extract();
    }

    public static String 회원_가입_요청하고_액세스_토큰_반환(OAuthRegisterCustomerCreateRequest request) {
        ExtractableResponse<Response> response = 회원_가입_요청(request);
        return response.jsonPath().getString("accessToken");
    }

    public static ExtractableResponse<Response> 회원_가입_요청(OAuthRegisterCustomerCreateRequest request) {
        return given()
                .log().all()
                .contentType(JSON)
                .body(request)

                .when()
                .post("/api/login/customer/register/test/token")

                .then()
                .log().all()
                .extract();
    }
}
