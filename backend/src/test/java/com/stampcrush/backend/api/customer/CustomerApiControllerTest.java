package com.stampcrush.backend.api.customer;

import com.stampcrush.backend.api.BaseControllerTest;
import io.restassured.RestAssured;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.payload.RequestFieldsSnippet;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.restassured.RestAssuredRestDocumentation.document;

class CustomerApiControllerTest extends BaseControllerTest {

    private static final RequestFieldsSnippet REQUEST_FIELDS = requestFields(
            fieldWithPath("phoneNumber").type(JsonFieldType.STRING).description("전화번호")
    );

    @Test
    void 임시_가입_고객을_생성한다() {
        JSONObject requestBody = new JSONObject();
        requestBody.put("phoneNumber", "01012345678");

        RestAssured.given(this.spec)
                .filter(document(DEFAULT_RESTDOC_PATH, REQUEST_FIELDS))
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .header("Content-type", "application/json")
                .body(requestBody)
                .log().all()

                .when()
                .post("/api/temporary-customers")

                .then()
                .statusCode(CREATED.value());
    }
}
