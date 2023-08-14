package com.stampcrush.backend.auth.application;

import com.stampcrush.backend.auth.*;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Profile("!test")
public class ManagerOAuthLoginService {

    private final OAuthOwnerRepository oAuthOwnerRepository;
    private final AuthTokensGenerator authTokensGenerator;
    private final ManagerOAuthService requestOAuthInfoService;

    public AuthTokens login(OAuthLoginParams params) {
        OAuthInfoResponse oAuthInfoResponse = requestOAuthInfoService.request(params);

        System.out.println("nickname is " + oAuthInfoResponse.getNickname());
        System.out.println("email is " + oAuthInfoResponse.getEmail());

        Long memberId = findOrCreateMember(oAuthInfoResponse);

        System.out.println("member id is " + memberId);

        return authTokensGenerator.generate(memberId);
    }

    private Long findOrCreateMember(OAuthInfoResponse oAuthInfoResponse) {
        return oAuthOwnerRepository.findByProfileNickname(oAuthInfoResponse.getNickname())
                .map(OAuthOwner::getId)
                .orElseGet(() -> OAuthOwner(oAuthInfoResponse));
    }

    private Long OAuthOwner(OAuthInfoResponse oAuthInfoResponse) {
        OAuthOwner oAuthOwner = OAuthOwner.builder()
                .profileNickname(oAuthInfoResponse.getNickname())
                .email(oAuthInfoResponse.getEmail())
                .oAuthProvider(oAuthInfoResponse.getOAuthProvider())
                .build();

        return oAuthOwnerRepository.save(oAuthOwner).getId();
    }
}
