package com.stampcrush.backend.acceptance;


import com.stampcrush.backend.api.manager.cafe.request.CafeCreateRequest;
import com.stampcrush.backend.api.manager.cafe.request.CafeUpdateRequest;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static com.stampcrush.backend.acceptance.step.ManagerCafeCreateStep.카페_생성_요청하고_아이디_반환;
import static com.stampcrush.backend.acceptance.step.ManagerCafeUpdateStep.카페_정보_업데이트_요청;
import static com.stampcrush.backend.acceptance.step.ManagerJoinStep.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

public class ManagerCafeCommandAcceptanceTest extends AcceptanceTest {

    @Test
    void 카페_사장이_자기_카페_정보_업데이트() {
        // given
        String ownerAccessToken = 카페_사장_회원_가입_요청하고_액세스_토큰_반환(OWNER_CREATE_REQUEST);

        CafeCreateRequest cafeCreateRequest = new CafeCreateRequest("woowacafe", "road", "detail", "1234");
        CafeUpdateRequest cafeUpdateRequest = new CafeUpdateRequest("hi", LocalTime.MIDNIGHT, LocalTime.NOON, "010123421253", "cafeimage");

        Long cafeId = 카페_생성_요청하고_아이디_반환(ownerAccessToken, cafeCreateRequest);

        // when
        ExtractableResponse<Response> response = 카페_정보_업데이트_요청(ownerAccessToken, cafeUpdateRequest, cafeId);

        // then
        assertThat(response.statusCode()).isEqualTo(200);
    }

    @Test
    void 카페_사장이_자기_카페_아닌_카페_정보_업데이트하면_에러_발생() {
        // given
        String ownerAccessToken = 카페_사장_회원_가입_요청하고_액세스_토큰_반환(OWNER_CREATE_REQUEST);
        String notOwnerAccessToken = 카페_사장_회원_가입_요청하고_액세스_토큰_반환(OWNER_CREATE_REQUEST_2);

        CafeCreateRequest cafeCreateRequest = new CafeCreateRequest("woowacafe", "road", "detail", "1234");
        CafeUpdateRequest cafeUpdateRequest = new CafeUpdateRequest("hi", LocalTime.MIDNIGHT, LocalTime.NOON, "010123421253", "cafeimage");

        Long cafeId = 카페_생성_요청하고_아이디_반환(ownerAccessToken, cafeCreateRequest);

        // when
        ExtractableResponse<Response> response = 카페_정보_업데이트_요청(notOwnerAccessToken, cafeUpdateRequest, cafeId);

        // then
        assertThat(response.statusCode()).isEqualTo(UNAUTHORIZED.value());
    }
}
