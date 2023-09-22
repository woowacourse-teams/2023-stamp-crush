package com.stampcrush.backend.acceptance;

import com.stampcrush.backend.api.manager.cafe.request.CafeCreateRequest;
import com.stampcrush.backend.api.manager.cafe.response.CafeFindResponse;
import com.stampcrush.backend.application.manager.cafe.dto.CafeFindResultDto;
import com.stampcrush.backend.auth.OAuthProvider;
import com.stampcrush.backend.auth.api.request.OAuthRegisterOwnerCreateRequest;
import com.stampcrush.backend.auth.application.util.AuthTokensGenerator;
import com.stampcrush.backend.entity.cafe.Cafe;
import com.stampcrush.backend.entity.user.Owner;
import com.stampcrush.backend.repository.cafe.CafeRepository;
import com.stampcrush.backend.repository.user.OwnerRepository;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import java.util.List;

import static com.stampcrush.backend.acceptance.step.ManagerCafeCreateStep.카페_생성_요청하고_아이디_반환;
import static com.stampcrush.backend.acceptance.step.ManagerCafeFindStep.자신의_카페_조회_요청;
import static com.stampcrush.backend.acceptance.step.ManagerJoinStep.카페_사장_회원_가입_요청하고_액세스_토큰_반환;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ManagerCafeFindAcceptanceTest extends AcceptanceTest {

    private static final CafeCreateRequest CAFE_CREATE_REQUEST = new CafeCreateRequest("깃짱카페", "서울시", "구체적인 주소", "127837267817");
    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private CafeRepository cafeRepository;

    @Autowired
    private AuthTokensGenerator authTokensGenerator;

    @Test
    void 카페_사장은_자신의_카페를_조회할_수_있다() {
        OAuthRegisterOwnerCreateRequest request = new OAuthRegisterOwnerCreateRequest("깃짱", OAuthProvider.KAKAO, 12314L);
        String accessToken = 카페_사장_회원_가입_요청하고_액세스_토큰_반환(request);
        Long ownerId = authTokensGenerator.extractMemberId(accessToken);
        Owner owner = ownerRepository.findById(ownerId).get();

        Long cafeId = 카페_생성_요청하고_아이디_반환(owner, CAFE_CREATE_REQUEST);
        Cafe cafe = cafeRepository.findById(cafeId).get();

        ExtractableResponse<Response> response = 자신의_카페_조회_요청(accessToken);
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
