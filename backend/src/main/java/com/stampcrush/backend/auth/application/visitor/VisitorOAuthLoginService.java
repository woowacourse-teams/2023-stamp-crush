package com.stampcrush.backend.auth.application.visitor;

import com.stampcrush.backend.auth.api.response.AuthTokensResponse;
import com.stampcrush.backend.auth.application.util.AuthTokensGenerator;
import com.stampcrush.backend.auth.application.util.OAuthLoginParams;
import com.stampcrush.backend.auth.client.OAuthInfoResponse;
import com.stampcrush.backend.entity.user.RegisterCustomer;
import com.stampcrush.backend.repository.user.RegisterCustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Profile("!test")
public class VisitorOAuthLoginService {

    private final RegisterCustomerRepository registerCustomerRepository;
    private final AuthTokensGenerator authTokensGenerator;
    private final VisitorOAuthService requestOAuthInfoService;

    public AuthTokensResponse login(OAuthLoginParams params) {
        OAuthInfoResponse oAuthInfoResponse = requestOAuthInfoService.request(params);
        Long memberId = findOrCreateCustomer(oAuthInfoResponse);
        return authTokensGenerator.generate(memberId);
    }

    private Long findOrCreateCustomer(OAuthInfoResponse oAuthInfo) {
        return registerCustomerRepository.findByOAuthProviderAndOAuthId(
                        oAuthInfo.getOAuthProvider(),
                        oAuthInfo.getOAuthId()
                )
                .map(RegisterCustomer::getId)
                .orElseGet(() -> createCustomer(oAuthInfo));
    }

    private Long createCustomer(OAuthInfoResponse oAuthInfo) {
        RegisterCustomer customer = RegisterCustomer.builder()
                .nickname(oAuthInfo.getNickname())
                .email(oAuthInfo.getEmail())
                .oAuthId(oAuthInfo.getOAuthId())
                .oAuthProvider(oAuthInfo.getOAuthProvider())
                .build();

        return registerCustomerRepository.save(customer).getId();
    }
}
