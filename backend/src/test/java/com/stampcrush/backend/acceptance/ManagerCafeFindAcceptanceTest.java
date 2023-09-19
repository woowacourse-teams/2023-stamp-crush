package com.stampcrush.backend.acceptance;

import com.stampcrush.backend.api.manager.cafe.request.CafeCreateRequest;
import com.stampcrush.backend.api.manager.cafe.response.CafeFindResponse;
import com.stampcrush.backend.api.manager.cafe.response.CafesFindResponse;
import com.stampcrush.backend.auth.OAuthProvider;
import com.stampcrush.backend.auth.api.request.OAuthRegisterOwnerCreateRequest;
import com.stampcrush.backend.auth.application.util.AuthTokensGenerator;
import com.stampcrush.backend.entity.user.Owner;
import com.stampcrush.backend.repository.user.OwnerRepository;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.stampcrush.backend.acceptance.step.ManagerCafeCreateStep.CAFE_CREATE_REQUEST;
import static com.stampcrush.backend.acceptance.step.ManagerCafeCreateStep.카페_생성_요청하고_아이디_반환;
import static com.stampcrush.backend.acceptance.step.ManagerCafeFindStep.오너의_모든_카페를_조회;
import static com.stampcrush.backend.acceptance.step.ManagerJoinStep.카페_사장_회원_가입_요청하고_액세스_토큰_반환;
import static org.assertj.core.api.Assertions.assertThat;

public class ManagerCafeFindAcceptanceTest extends AcceptanceTest {

    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private AuthTokensGenerator authTokensGenerator;

    @Test
    void 카페_사장은_카페의_현재_쿠폰_디자인_정책_조회_가능하다() {
        // given
        String ownerToken = 카페_사장_회원_가입_요청하고_액세스_토큰_반환(new OAuthRegisterOwnerCreateRequest("leo", OAuthProvider.KAKAO, 123L));
        Long ownerId = authTokensGenerator.extractMemberId(ownerToken);
        Owner owner = ownerRepository.findById(ownerId).get();

        Long cafeId = 카페_생성_요청하고_아이디_반환(owner, CAFE_CREATE_REQUEST);
        Long leoCafeId = 카페_생성_요청하고_아이디_반환(owner, new CafeCreateRequest("leo cafe", "송파구", "새말로", "안알랴줌"));

        // when
        System.out.println("실제 시작");
        ExtractableResponse<Response> response = 오너의_모든_카페를_조회(owner);
        System.out.println("끝");
        CafesFindResponse cafesFindResponse = response.body().as(CafesFindResponse.class);

        // then
        CafeFindResponse expected1 = new CafeFindResponse(null, "leo cafe", null, null, null, null, "송파구", "새말로", "안알랴줌", null);
        CafeFindResponse expected2 = new CafeFindResponse(null, CAFE_CREATE_REQUEST.getName(), null, null, null, null, CAFE_CREATE_REQUEST.getRoadAddress(), CAFE_CREATE_REQUEST.getDetailAddress(), CAFE_CREATE_REQUEST.getBusinessRegistrationNumber(), null);

        assertThat(cafesFindResponse.getCafes()).usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "openTime", "closeTime", "telephoneNumber", "cafeImageUrl", "introduction")
                .containsExactlyInAnyOrder(expected1, expected2);
    }
}
