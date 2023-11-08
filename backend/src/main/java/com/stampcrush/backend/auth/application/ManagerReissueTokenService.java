package com.stampcrush.backend.auth.application;

import com.stampcrush.backend.auth.api.response.AuthTokensResponse;
import com.stampcrush.backend.auth.application.util.AuthTokensGenerator;
import com.stampcrush.backend.auth.repository.BlackListRepository;
import com.stampcrush.backend.exception.UnAuthorizationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class ManagerReissueTokenService {

    private final BlackListRepository blackListRepository;
    private final AuthTokensGenerator authTokensGenerator;

    public AuthTokensResponse reissueToken(final String refreshToken) {
        if (!authTokensGenerator.isValidToken(refreshToken)) {
            throw new UnAuthorizationException("[ERROR] 비정상 Refresh Token입니다!");
        }
        if (!blackListRepository.isValidRefreshToken(refreshToken)) {
            throw new UnAuthorizationException("[ERROR] 이미 로그아웃한 사용자입니다. 다시 로그인해 주세요");
        }

        final Long memberId = authTokensGenerator.extractMemberId(refreshToken);
        return authTokensGenerator.generate(memberId);
    }
}
