package com.stampcrush.backend.acceptance.step;

import com.stampcrush.backend.api.manager.reward.request.RewardUsedUpdateRequest;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;

public class ManagerRewardStep {

    public static ExtractableResponse<Response> 리워드_사용(String accessToken, RewardUsedUpdateRequest rewardUsedUpdateRequest, Long customerId, Long rewardId) {
        return given()
                .log().all()
                .contentType(JSON)
                .body(rewardUsedUpdateRequest)
                .auth().preemptive()
                .oauth2(accessToken)

                .when()
                .patch("/api/admin/customers/" + customerId + "/rewards/" + rewardId)
                .then()
                .log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 리워드_목록_조회(String accessToken, Long cafeId, Long customerId) {
        return given()
                .log().all()
                .queryParam("cafe-id", cafeId)
                .queryParam("used", false)
                .contentType(JSON)
                .auth().preemptive()
                .oauth2(accessToken)

                .when()
                .get("/api/admin/customers/" + customerId + "/rewards")
                .then()
                .log().all()
                .extract();
    }
}
