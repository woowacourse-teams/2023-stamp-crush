package com.stampcrush.backend.auth.test;

import com.stampcrush.backend.auth.OAuthProvider;
import com.stampcrush.backend.auth.api.response.AuthTokensResponse;
import com.stampcrush.backend.auth.application.util.AuthTokensGenerator;
import com.stampcrush.backend.entity.user.Customer;
import com.stampcrush.backend.repository.user.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile("test")
@RequiredArgsConstructor
@Service
public class VisitorAuthTestService {

    private final CustomerRepository customerRepository;
    private final AuthTokensGenerator authTokensGenerator;

    public AuthTokensResponse join(String nickname, String email, OAuthProvider oAuthProvider, Long oAuthId) {
        Customer customer = Customer.registeredCustomerBuilder()
                .nickname(nickname)
                .email(email)
                .oAuthProvider(oAuthProvider)
                .oAuthId(oAuthId)
                .build();

        Customer savedCustomer = customerRepository.save(customer);
        Long customerId = savedCustomer.getId();
        return authTokensGenerator.generate(customerId);
    }
}
