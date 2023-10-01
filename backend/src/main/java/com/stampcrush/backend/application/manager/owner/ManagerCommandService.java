package com.stampcrush.backend.application.manager.owner;

import com.stampcrush.backend.application.manager.owner.dto.OwnerCreateDto;
import com.stampcrush.backend.application.manager.owner.dto.OwnerLoginDto;
import com.stampcrush.backend.auth.api.response.AuthTokensResponse;
import com.stampcrush.backend.auth.application.util.AuthTokensGenerator;
import com.stampcrush.backend.auth.entity.BlackList;
import com.stampcrush.backend.auth.repository.BlackListRepository;
import com.stampcrush.backend.entity.user.Owner;
import com.stampcrush.backend.exception.LoginIdBadRequestException;
import com.stampcrush.backend.exception.OwnerNotFoundException;
import com.stampcrush.backend.exception.UnAuthorizationException;
import com.stampcrush.backend.repository.user.OwnerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class ManagerCommandService {

    private final OwnerRepository ownerRepository;
    private final BlackListRepository blackListRepository;
    private final AuthTokensGenerator authTokensGenerator;

    public Long register(OwnerCreateDto ownerCreateDto) {
        String loginId = ownerCreateDto.getLoginId();
        String password = ownerCreateDto.getPassword();

        if (isDuplicatedLoginId(loginId)) {
            throw new LoginIdBadRequestException("중복되는 아이디 입니다.");
        }
        Owner owner = Owner.of(loginId, password);
        return ownerRepository.save(owner).getId();
    }

    private boolean isDuplicatedLoginId(String loginId) {
        return ownerRepository.findByLoginId(loginId).isPresent();
    }

    public AuthTokensResponse login(OwnerLoginDto ownerLoginDto) {
        Owner owner = ownerRepository.findByLoginId(ownerLoginDto.getLoginId())
                .orElseThrow(() -> new OwnerNotFoundException("존재하지 않는 아이디 입니다."));
        owner.checkPassword(ownerLoginDto.getPassword());
        return authTokensGenerator.generate(owner.getId());
    }

    public AuthTokensResponse reissueToken(Long ownerId, String refreshToken) {
        if (authTokensGenerator.isValidToken(refreshToken) && blackListRepository.isValidRefreshToken(refreshToken)) {
            return authTokensGenerator.generate(ownerId);
        }

        throw new UnAuthorizationException("accessToken 재발급이 불가능합니다");
    }

    public void logout(String refreshToken) {
        blackListRepository.save(new BlackList(refreshToken));
    }
}
