package com.stampcrush.backend.acceptance.step;

import com.stampcrush.backend.api.manager.customer.request.TemporaryCustomerCreateRequest;
import com.stampcrush.backend.entity.user.Owner;
import com.stampcrush.backend.helper.BearerAuthHelper;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

import static io.restassured.http.ContentType.JSON;

public class ManagerTemporaryCustomerCreateStep {

    public static final TemporaryCustomerCreateRequest TEMPORARY_CUSTOMER_CREATE_REQUEST = new TemporaryCustomerCreateRequest("01012345678");

    public static Long 전화번호로_임시_고객_등록_요청하고_아이디_반환(Owner owner, TemporaryCustomerCreateRequest request) {
        ExtractableResponse<Response> response = 전화번호로_임시_고객_등록_요청(owner, request);
        return Long.valueOf(response.header("Location").split("/")[2]);
    }

    public static ExtractableResponse<Response> 전화번호로_임시_고객_등록_요청(Owner owner, TemporaryCustomerCreateRequest request) {
        return RestAssured.given()
                .log().all()
                .contentType(JSON)
                .body(request)
                .auth().preemptive()
                .oauth2(BearerAuthHelper.generateToken(owner.getId()))

//                .basic(owner.getLoginId(), owner.getEncryptedPassword())

                .when()
                .post("/api/admin/temporary-customers")

                .then()
                .log().all()
                .extract();
    }
}
