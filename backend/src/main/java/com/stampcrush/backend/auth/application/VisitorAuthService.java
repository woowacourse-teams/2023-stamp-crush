package com.stampcrush.backend.auth.application;

import com.stampcrush.backend.auth.OAuthProvider;
import com.stampcrush.backend.auth.api.response.AuthTokensResponse;
import com.stampcrush.backend.auth.application.util.AuthTokensGenerator;
import com.stampcrush.backend.entity.user.RegisterCustomer;
import com.stampcrush.backend.repository.user.RegisterCustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class VisitorAuthService {

    private final RegisterCustomerRepository registerCustomerRepository;
    private final AuthTokensGenerator authTokensGenerator;

    public AuthTokensResponse join(String nickname, String email, OAuthProvider oAuthProvider, Long oAuthId) {
        RegisterCustomer customer = RegisterCustomer.builder()
                .nickname(nickname)
                .email(email)
                .oAuthProvider(oAuthProvider)
                .oAuthId(oAuthId)
                .build();
        RegisterCustomer savedCustomer = registerCustomerRepository.save(customer);
        Long customerId = savedCustomer.getId();
        return authTokensGenerator.generate(customerId);
    }
}
