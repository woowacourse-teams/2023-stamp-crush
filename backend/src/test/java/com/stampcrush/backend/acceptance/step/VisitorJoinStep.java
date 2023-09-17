package com.stampcrush.backend.acceptance.step;

import com.stampcrush.backend.auth.OAuthProvider;
import com.stampcrush.backend.auth.api.request.OAuthRegisterCustomerCreateRequest;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;

public class VisitorJoinStep {

    public static final OAuthRegisterCustomerCreateRequest O_AUTH_REGISTER_CUSTOMER_CREATE_REQUEST_JENA = new OAuthRegisterCustomerCreateRequest(
            "jena",
            "jena@gmail.com",
            OAuthProvider.KAKAO,
            938272L
    );

    public static Long 임시_고객_회원_가입_요청하고_아이디_반환(String phoneNumber) {
        ExtractableResponse<Response> response = 임시_고객_회원_가입_요청(phoneNumber);
        String location = response.header("Location");
        return Long.valueOf(location.split("/")[2]);
    }

    public static ExtractableResponse<Response> 임시_고객_회원_가입_요청(String phoneNumber) {
        return RestAssured.given()
                .log().all()
                .contentType(JSON)

                .when()
                .post("/api/login/temporary/test?phone-number=" + phoneNumber)

                .then()
                .log().all()
                .extract();
    }

    public static String 가입_고객_회원_가입_요청하고_액세스_토큰_반환(OAuthRegisterCustomerCreateRequest request) {
        ExtractableResponse<Response> response = 가입_고객_회원_가입_요청(request);
        return response.jsonPath().getString("accessToken");
    }

    public static ExtractableResponse<Response> 가입_고객_회원_가입_요청(OAuthRegisterCustomerCreateRequest request) {
        return given()
                .log().all()
                .contentType(JSON)
                .body(request)

                .when()
                .post("/api/login/register/test/token")

                .then()
                .log().all()
                .extract();
    }
}
