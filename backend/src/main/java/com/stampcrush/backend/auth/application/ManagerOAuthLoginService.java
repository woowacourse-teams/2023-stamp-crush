package com.stampcrush.backend.auth.application;

import com.stampcrush.backend.auth.api.response.AuthTokensResponse;
import com.stampcrush.backend.auth.application.util.AuthTokensGenerator;
import com.stampcrush.backend.auth.application.util.OAuthLoginParams;
import com.stampcrush.backend.auth.client.OAuthInfoResponse;
import com.stampcrush.backend.entity.user.Owner;
import com.stampcrush.backend.repository.user.OwnerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Profile("!test")
public class ManagerOAuthLoginService {

    private final OwnerRepository ownerRepository;
    private final AuthTokensGenerator authTokensGenerator;
    private final ManagerOAuthService requestOAuthInfoService;

    public AuthTokensResponse login(OAuthLoginParams params) {
        OAuthInfoResponse oAuthInfoResponse = requestOAuthInfoService.request(params);

        System.out.println("nickname is " + oAuthInfoResponse.getNickname());
        System.out.println("email is " + oAuthInfoResponse.getEmail());

        Long memberId = findOrCreateOwner(oAuthInfoResponse);

        System.out.println("member id is " + memberId);

        return authTokensGenerator.generate(memberId);
    }

    private Long findOrCreateOwner(OAuthInfoResponse oAuthInfoResponse) {
        return ownerRepository.findByNickname(oAuthInfoResponse.getNickname())
                .map(Owner::getId)
                .orElseGet(() -> createOwner(oAuthInfoResponse));
    }

    private Long createOwner(OAuthInfoResponse oAuthInfoResponse) {
        Owner oAuthOwner = Owner.builder()
                .nickname(oAuthInfoResponse.getNickname())
                .build();

        return ownerRepository.save(oAuthOwner).getId();
    }
}
