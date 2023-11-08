package com.stampcrush.backend.auth.application;

import com.stampcrush.backend.auth.entity.BlackList;
import com.stampcrush.backend.auth.repository.BlackListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class ManagerLogoutService {

    private final BlackListRepository blackListRepository;
    private final RefreshTokenValidator refreshTokenValidator;

    public void logout(Long id, String refreshToken) {
        refreshTokenValidator.validateToken(refreshToken);
        refreshTokenValidator.validateTokenOwnerId(refreshToken, id);
        refreshTokenValidator.validateLogoutToken(refreshToken);
        blackListRepository.save(new BlackList(refreshToken));
    }
}
