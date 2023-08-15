package com.stampcrush.backend.auth.application.manager;

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

        Long memberId = findOrCreateOwner_temp(oAuthInfoResponse);

        System.out.println("member id is " + memberId);

        return authTokensGenerator.generate(memberId);
    }

    private Long findOrCreateOwner_temp(OAuthInfoResponse oAuthInfo) {
        return ownerRepository.findByNickname(oAuthInfo.getNickname())
                .map(Owner::getId)
                .orElseGet(() -> createOwner_temp(oAuthInfo));
    }

    private Long findOrCreateOwner_real(OAuthInfoResponse oAuthInfo) {
        return ownerRepository.findByOAuthProviderAndOAuthId(
                        oAuthInfo.getOAuthProvider(),
                        oAuthInfo.getOAuthId()
                )
                .map(Owner::getId)
                .orElseGet(() -> createOwner_real(oAuthInfo));
    }

    private Long createOwner_temp(OAuthInfoResponse oAuthInfo) {
        Owner oAuthOwner = Owner.builder()
                .nickname(oAuthInfo.getNickname())
                .build();

        return ownerRepository.save(oAuthOwner).getId();
    }

    private Long createOwner_real(OAuthInfoResponse oAuthInfo) {
        Owner oAuthOwner = Owner.builder()
                .nickname(oAuthInfo.getNickname())
                .email(oAuthInfo.getEmail())
                .oAuthId(oAuthInfo.getOAuthId())
                .oAuthProvider(oAuthInfo.getOAuthProvider())
                .build();

        return ownerRepository.save(oAuthOwner).getId();
    }
}
