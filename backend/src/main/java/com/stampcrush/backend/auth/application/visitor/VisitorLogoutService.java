package com.stampcrush.backend.auth.application.visitor;

import com.stampcrush.backend.auth.application.util.AuthTokensGenerator;
import com.stampcrush.backend.auth.entity.BlackList;
import com.stampcrush.backend.auth.repository.BlackListRepository;
import com.stampcrush.backend.exception.CustomerUnAuthorizationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@RequiredArgsConstructor
@Service
public class VisitorLogoutService {

    private final AuthTokensGenerator authTokensGenerator;
    private final BlackListRepository blackListRepository;

    public void logout(Long customerId, String refreshToken) {
        validateRefreshToken(customerId, refreshToken);
        blackListRepository.save(new BlackList(refreshToken));
    }

    private void validateRefreshToken(Long customerId, String refreshToken) {
        Long id = authTokensGenerator.extractMemberId(refreshToken);
        if (!Objects.equals(customerId, id)) {
            throw new CustomerUnAuthorizationException("해당 사용자의 refresh token이 아닙니다!");
        }
    }
}
