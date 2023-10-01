package com.stampcrush.backend.auth.application.visitor;

import com.stampcrush.backend.auth.application.util.AuthTokensGenerator;
import com.stampcrush.backend.auth.repository.BlackListRepository;
import com.stampcrush.backend.exception.UnAuthorizationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VisitorAuthLoginServiceTest {

    @InjectMocks
    private VisitorAuthLoginService visitorAuthLoginService;

    @Mock
    private BlackListRepository blackListRepository;
    @Mock
    private AuthTokensGenerator authTokensGenerator;

    @Test
    void 유효하고_블랙리스트에_없는_refreshToken이면_새로_토큰들을_발급해준다() {
        String refreshToken = "validToken";

        when(authTokensGenerator.isValidToken(refreshToken)).thenReturn(true);
        when(authTokensGenerator.extractMemberId(refreshToken)).thenReturn(1L);
        when(blackListRepository.isValidRefreshToken(refreshToken)).thenReturn(true);

        assertDoesNotThrow(() -> visitorAuthLoginService.reissueToken(refreshToken));
    }

    @Test
    void 만료된_refreshToken이면_토큰_재발급_불가() {
        String refreshToken = "expiredToken";

        when(authTokensGenerator.isValidToken(refreshToken)).thenReturn(false);

        assertThatThrownBy(() -> visitorAuthLoginService.reissueToken(refreshToken)).isInstanceOf(UnAuthorizationException.class);
    }

    @Test
    void 블랙리스트에_존재하는_refreshToken이면_토큰_재발급_불가() {
        String refreshToken = "blacklistToken";

        when(authTokensGenerator.isValidToken(refreshToken)).thenReturn(true);
        when(blackListRepository.isValidRefreshToken(refreshToken)).thenReturn(false);

        assertThatThrownBy(() -> visitorAuthLoginService.reissueToken(refreshToken)).isInstanceOf(UnAuthorizationException.class);
    }
}
