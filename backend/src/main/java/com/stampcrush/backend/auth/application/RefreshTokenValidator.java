package com.stampcrush.backend.auth.application;

import com.stampcrush.backend.auth.application.util.AuthTokensGenerator;
import com.stampcrush.backend.auth.repository.BlackListRepository;
import com.stampcrush.backend.exception.UnAuthorizationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class RefreshTokenValidator {

    private final AuthTokensGenerator authTokensGenerator;
    private final BlackListRepository blackListRepository;

    public void validateToken(String refreshToken) {
        if (!authTokensGenerator.isValidToken(refreshToken)) {
            throw new UnAuthorizationException("[ERROR] 유효하지 않은 Refresh Token입니다!");
        }
    }

    public void validateTokenOwnerId(String refreshToken, Long id) {
        final Long ownerId = authTokensGenerator.extractMemberId(refreshToken);
        if (!ownerId.equals(id)) {
            throw new UnAuthorizationException("[ERROR] 로그인한 사용자의 Refresh Token이 아닙니다!");
        }
    }

    public void validateLogoutToken(String refreshToken) {
        if (blackListRepository.existsByInvalidRefreshToken(refreshToken)) {
            throw new UnAuthorizationException("[ERROR] 이미 로그아웃된 사용자입니다!");
        }
    }
}
