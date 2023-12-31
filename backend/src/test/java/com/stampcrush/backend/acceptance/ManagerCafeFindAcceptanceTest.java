package com.stampcrush.backend.acceptance;

import com.stampcrush.backend.api.manager.cafe.request.CafeCreateRequest;
import com.stampcrush.backend.api.manager.cafe.response.CafeFindResponse;
import com.stampcrush.backend.api.manager.cafe.response.CafesFindResponse;
import com.stampcrush.backend.application.manager.cafe.dto.CafeFindResultDto;
import com.stampcrush.backend.auth.OAuthProvider;
import com.stampcrush.backend.auth.api.request.OAuthRegisterOwnerCreateRequest;
import com.stampcrush.backend.entity.cafe.Cafe;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.util.List;

import static com.stampcrush.backend.acceptance.step.ManagerCafeCreateStep.CAFE_CREATE_REQUEST;
import static com.stampcrush.backend.acceptance.step.ManagerCafeCreateStep.카페_생성_요청하고_아이디_반환;
import static com.stampcrush.backend.acceptance.step.ManagerCafeFindStep.자신의_카페_조회_요청;
import static com.stampcrush.backend.acceptance.step.ManagerJoinStep.카페_사장_회원_가입_요청하고_액세스_토큰_반환;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ManagerCafeFindAcceptanceTest extends AcceptanceTest {

    @Test
    void 카페_사장은_카페의_현재_쿠폰_디자인_정책_조회_가능하다() {
        // given
        String ownerToken = 카페_사장_회원_가입_요청하고_액세스_토큰_반환(new OAuthRegisterOwnerCreateRequest("leo", OAuthProvider.KAKAO, 123L));

        Long cafeId = 카페_생성_요청하고_아이디_반환(ownerToken, CAFE_CREATE_REQUEST);
        Long leoCafeId = 카페_생성_요청하고_아이디_반환(ownerToken, new CafeCreateRequest("leo cafe", "송파구", "새말로", "안알랴줌"));

        // when
        ExtractableResponse<Response> response = 자신의_카페_조회_요청(ownerToken);
        CafesFindResponse cafesFindResponse = response.body().as(CafesFindResponse.class);

        // then
        CafeFindResponse expected1 = new CafeFindResponse(null, "leo cafe", null, null, null, null, "송파구", "새말로", "안알랴줌", null);
        CafeFindResponse expected2 = new CafeFindResponse(null, CAFE_CREATE_REQUEST.getName(), null, null, null, null, CAFE_CREATE_REQUEST.getRoadAddress(), CAFE_CREATE_REQUEST.getDetailAddress(), CAFE_CREATE_REQUEST.getBusinessRegistrationNumber(), null);

        assertThat(cafesFindResponse.getCafes()).usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "openTime", "closeTime", "telephoneNumber", "cafeImageUrl", "introduction")
                .containsExactlyInAnyOrder(expected1, expected2);
    }

    @Test
    void 카페_사장은_자신의_카페를_조회할_수_있다() {
        OAuthRegisterOwnerCreateRequest request = new OAuthRegisterOwnerCreateRequest("깃짱", OAuthProvider.KAKAO, 12314L);
        String ownerAcessToken = 카페_사장_회원_가입_요청하고_액세스_토큰_반환(request);

        Long cafeId = 카페_생성_요청하고_아이디_반환(ownerAcessToken, CAFE_CREATE_REQUEST);
        Cafe cafe = cafeRepository.findById(cafeId).get();

        ExtractableResponse<Response> response = 자신의_카페_조회_요청(ownerAcessToken);
        List<CafeFindResponse> cafes = response.jsonPath()
                .getList("cafes", CafeFindResponse.class);

        assertAll(
                () -> assertEquals(response.statusCode(), HttpStatus.OK.value()),
                () -> assertThat(cafes)
                        .usingRecursiveComparison()
                        .isEqualTo(
                                List.of(CafeFindResponse.from(CafeFindResultDto.from(cafe)))
                        )
        );
    }
}
