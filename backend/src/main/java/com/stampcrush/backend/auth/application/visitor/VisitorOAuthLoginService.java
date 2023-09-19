package com.stampcrush.backend.auth.application.visitor;

import com.stampcrush.backend.auth.api.response.AuthTokensResponse;
import com.stampcrush.backend.auth.application.util.AuthTokensGenerator;
import com.stampcrush.backend.auth.application.util.OAuthLoginParams;
import com.stampcrush.backend.auth.client.OAuthInfoResponse;
import com.stampcrush.backend.entity.user.Customer;
import com.stampcrush.backend.repository.user.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Profile("!test")
public class VisitorOAuthLoginService {

    private final CustomerRepository customerRepository;
    private final AuthTokensGenerator authTokensGenerator;
    private final VisitorOAuthService requestOAuthInfoService;

    public AuthTokensResponse login(OAuthLoginParams params) {
        OAuthInfoResponse oAuthInfoResponse = requestOAuthInfoService.request(params);
        Long memberId = findOrCreateCustomer(oAuthInfoResponse);
        return authTokensGenerator.generate(memberId);
    }

    private Long findOrCreateCustomer(OAuthInfoResponse oAuthInfo) {
        return customerRepository.findByOAuthProviderAndOAuthId(
                        oAuthInfo.getOAuthProvider(),
                        oAuthInfo.getOAuthId()
                )
                .map(Customer::getId)
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
}
