package com.stampcrush.backend.acceptance;

import com.stampcrush.backend.auth.OAuthProvider;
import com.stampcrush.backend.auth.api.request.OAuthRegisterOwnerCreateRequest;
import com.stampcrush.backend.auth.application.util.AuthTokensGenerator;
import com.stampcrush.backend.entity.user.Owner;
import com.stampcrush.backend.repository.user.OwnerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.stampcrush.backend.acceptance.step.ManagerJoinStep.카페_사장_회원_가입_요청하고_액세스_토큰_반환;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ManagerJoinAcceptanceTest extends AcceptanceTest {

    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private AuthTokensGenerator authTokensGenerator;

    @Test
    void 카페_사장이_회원가입_한다() {
        OAuthRegisterOwnerCreateRequest request = new OAuthRegisterOwnerCreateRequest("깃짱", OAuthProvider.KAKAO, 12134L);
        String accessToken = 카페_사장_회원_가입_요청하고_액세스_토큰_반환(request);
        Long ownerId = authTokensGenerator.extractMemberId(accessToken);

        Owner findOwner = ownerRepository.findById(ownerId).get();

        assertAll(
                () -> assertEquals(findOwner.getNickname(), request.getNickname()),
                () -> assertEquals(findOwner.getOAuthProvider(), request.getoAuthProvider()),
                () -> assertEquals(findOwner.getOAuthId(), request.getoAuthId())
        );
    }
}
