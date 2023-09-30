package com.stampcrush.backend.auth.test;

import com.stampcrush.backend.auth.OAuthProvider;
import com.stampcrush.backend.auth.api.response.AuthTokensResponse;
import com.stampcrush.backend.auth.application.util.AuthTokensGenerator;
import com.stampcrush.backend.entity.user.Owner;
import com.stampcrush.backend.repository.user.OwnerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile("test")
@RequiredArgsConstructor
@Service
public class ManagerAuthTestService {

    private final OwnerRepository ownerRepository;
    private final AuthTokensGenerator authTokensGenerator;

    public AuthTokensResponse join(String nickname, OAuthProvider oAuthProvider, Long oAuthId) {
        Owner owner = Owner.builder()
                .nickname(nickname)
                .oAuthProvider(oAuthProvider)
                .oAuthId(oAuthId)
                .build();
        Owner savedOwner = ownerRepository.save(owner);
        Long ownerId = savedOwner.getId();
        return authTokensGenerator.generate(ownerId);
    }
}
