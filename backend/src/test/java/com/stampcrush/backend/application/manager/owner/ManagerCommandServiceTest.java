package com.stampcrush.backend.application.manager.owner;

import com.stampcrush.backend.application.ServiceSliceTest;
import com.stampcrush.backend.application.manager.owner.dto.OwnerCreateDto;
import com.stampcrush.backend.application.manager.owner.dto.OwnerLoginDto;
import com.stampcrush.backend.application.manager.owner.util.PasswordEncryptor;
import com.stampcrush.backend.auth.api.response.AuthTokensResponse;
import com.stampcrush.backend.auth.application.util.AuthTokensGenerator;
import com.stampcrush.backend.entity.user.Owner;
import com.stampcrush.backend.exception.LoginIdBadRequestException;
import com.stampcrush.backend.exception.OwnerUnAuthorizationException;
import com.stampcrush.backend.repository.user.OwnerRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ServiceSliceTest
class ManagerCommandServiceTest {

    @InjectMocks
    private ManagerCommandService managerCommandService;

    @Mock
    private OwnerRepository ownerRepository;

    @Mock
    private AuthTokensGenerator authTokensGenerator;

    @Test
    void 이미_존재하는_로그인_아이디면_예외를_던진다() {
        // given
        OwnerCreateDto ownerCreateDto = new OwnerCreateDto("loginId", "newpass123");
        Owner existOwner = Owner.of("loginId", "password123!");

        when(ownerRepository.findByLoginId(existOwner.getLoginId()))
                .thenReturn(Optional.of(existOwner));

        // when, then
        assertThatThrownBy(() -> managerCommandService.register(ownerCreateDto))
                .isInstanceOf(LoginIdBadRequestException.class);
    }

    @Test
    void 비밀번호가_일치하면_토큰을_반환한다() {
        // given
        OwnerLoginDto ownerLoginDto = new OwnerLoginDto("loginId", "newpass123");
        String encryptedPassword = PasswordEncryptor.encrypt(ownerLoginDto.getPassword());
        Owner existOwner = new Owner("nickname", ownerLoginDto.getLoginId(), encryptedPassword, "phone");

        when(ownerRepository.findByLoginId(existOwner.getLoginId()))
                .thenReturn(Optional.of(existOwner));
        when(authTokensGenerator.generate(null))
                .thenReturn(new AuthTokensResponse("realtoken", "a", "a", 1L));

        // when
        AuthTokensResponse response = managerCommandService.login(ownerLoginDto);

        // then
        assertThat(response.getAccessToken()).isEqualTo("realtoken");
    }

    @Test
    void 비밀번호가_일치하지_않으면_예외를_던진다() {
        // given
        OwnerLoginDto ownerLoginDto = new OwnerLoginDto("loginId", "newpass123");
        String encryptedPassword = PasswordEncryptor.encrypt("wrongPassword");
        Owner existOwner = new Owner("nickname", ownerLoginDto.getLoginId(), encryptedPassword, "phone");

        when(ownerRepository.findByLoginId(existOwner.getLoginId()))
                .thenReturn(Optional.of(existOwner));

        // when, then
        Assertions.assertThatThrownBy(() -> managerCommandService.login(ownerLoginDto))
                .isInstanceOf(OwnerUnAuthorizationException.class);
    }
}
