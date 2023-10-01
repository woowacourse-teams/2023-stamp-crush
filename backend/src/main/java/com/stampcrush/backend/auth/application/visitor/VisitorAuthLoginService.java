package com.stampcrush.backend.auth.application.visitor;

import com.stampcrush.backend.auth.api.response.AuthTokensResponse;
import com.stampcrush.backend.auth.application.util.AuthTokensGenerator;
import com.stampcrush.backend.auth.application.util.OAuthLoginParams;
import com.stampcrush.backend.auth.client.OAuthInfoResponse;
import com.stampcrush.backend.auth.repository.BlackListRepository;
import com.stampcrush.backend.entity.user.Customer;
import com.stampcrush.backend.exception.UnAuthorizationException;
import com.stampcrush.backend.repository.user.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class VisitorAuthLoginService {

    private final CustomerRepository customerRepository;
    private final BlackListRepository blackListRepository;
    private final AuthTokensGenerator authTokensGenerator;
    private final VisitorOAuthService requestOAuthInfoService;

    public AuthTokensResponse login(OAuthLoginParams params) {
        OAuthInfoResponse oAuthInfoResponse = requestOAuthInfoService.request(params);
        Long memberId = findOrCreateCustomer(oAuthInfoResponse);
        return authTokensGenerator.generate(memberId);
    }

    private Long findOrCreateCustomer(OAuthInfoResponse oAuthInfo) {
        return customerRepository.findIdByOAuthProviderAndOAuthId(
                        oAuthInfo.getOAuthProvider(),
                        oAuthInfo.getOAuthId()
                )
                .orElseGet(() -> createCustomer(oAuthInfo));
    }

    private Long createCustomer(OAuthInfoResponse oAuthInfo) {
        Customer customer = Customer.registeredCustomerBuilder()
                .nickname(oAuthInfo.getNickname())
                .email(oAuthInfo.getEmail())
                .oAuthProvider(oAuthInfo.getOAuthProvider())
                .oAuthId(oAuthInfo.getOAuthId())
                .build();

        return customerRepository.save(customer).getId();
    }

    public AuthTokensResponse reissueToken(String refreshToken) {
        if (authTokensGenerator.isValidToken(refreshToken) && blackListRepository.isValidRefreshToken(refreshToken)) {
            Long memberId = authTokensGenerator.extractMemberId(refreshToken);
            return authTokensGenerator.generate(memberId);
        }

        throw new UnAuthorizationException("accessToken 재발급이 불가능합니다");
    }
}
