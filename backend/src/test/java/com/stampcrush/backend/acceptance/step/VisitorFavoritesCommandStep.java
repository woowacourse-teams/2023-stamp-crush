package com.stampcrush.backend.acceptance.step;

import com.stampcrush.backend.api.visitor.favorites.request.FavoritesUpdateRequest;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;

public class VisitorFavoritesCommandStep {

    public static ExtractableResponse<Response> 즐겨찾기_등록(String accessToken, FavoritesUpdateRequest favoritesUpdateRequest, Long cafeId) {
        return given()
                .contentType(JSON)
                .body(favoritesUpdateRequest)
                .auth().preemptive()
                .oauth2(accessToken)
                .when()
                .post("/api/cafes/" + cafeId + "/favorites")
                .then()
                .extract();
    }
}
