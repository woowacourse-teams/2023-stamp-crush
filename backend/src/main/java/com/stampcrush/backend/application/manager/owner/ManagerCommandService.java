package com.stampcrush.backend.application.manager.owner;

import com.stampcrush.backend.application.manager.owner.dto.OwnerCreateDto;
import com.stampcrush.backend.application.manager.owner.dto.OwnerLoginDto;
import com.stampcrush.backend.auth.api.response.AuthTokensResponse;
import com.stampcrush.backend.auth.application.util.AuthTokensGenerator;
import com.stampcrush.backend.entity.user.Owner;
import com.stampcrush.backend.exception.LoginIdBadRequestException;
import com.stampcrush.backend.exception.OwnerNotFoundException;
import com.stampcrush.backend.repository.user.OwnerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class ManagerCommandService {

    private final OwnerRepository ownerRepository;
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

    //TODO: 리프레쉬 토큰이 데이터베이스에 저장되면 readOnly 제거
    @Transactional(readOnly = true)
    public AuthTokensResponse login(OwnerLoginDto ownerLoginDto) {
        Owner owner = ownerRepository.findByLoginId(ownerLoginDto.getLoginId())
                .orElseThrow(() -> new OwnerNotFoundException("존재하지 않는 아이디 입니다."));
        owner.checkPassword(ownerLoginDto.getPassword());
        return authTokensGenerator.generate(owner.getId());
    }
}
