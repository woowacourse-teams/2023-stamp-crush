package com.stampcrush.backend.acceptance;

import com.stampcrush.backend.entity.user.Owner;
import org.junit.jupiter.api.Test;

import static com.stampcrush.backend.acceptance.step.ManagerJoinStep.OWNER_CREATE_REQUEST;
import static com.stampcrush.backend.acceptance.step.ManagerJoinStep.카페_사장_회원_가입_요청하고_액세스_토큰_반환;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ManagerJoinAcceptanceTest extends AcceptanceTest {

    @Test
    void 카페_사장이_회원가입_한다() {
        String accessToken = 카페_사장_회원_가입_요청하고_액세스_토큰_반환(OWNER_CREATE_REQUEST);
        Long ownerId = authTokensGenerator.extractMemberId(accessToken);

        Owner findOwner = ownerRepository.findById(ownerId).get();

        assertAll(
                () -> assertEquals(findOwner.getNickname(), OWNER_CREATE_REQUEST.getNickname()),
                () -> assertEquals(findOwner.getOAuthProvider(), OWNER_CREATE_REQUEST.getoAuthProvider()),
                () -> assertEquals(findOwner.getOAuthId(), OWNER_CREATE_REQUEST.getoAuthId())
        );
    }
}
