package com.stampcrush.backend.dev;

import com.stampcrush.backend.auth.OAuthProvider;
import com.stampcrush.backend.auth.api.response.AuthTokensResponse;
import com.stampcrush.backend.auth.application.util.AuthTokensGenerator;
import com.stampcrush.backend.entity.user.Customer;
import com.stampcrush.backend.entity.user.Owner;
import com.stampcrush.backend.repository.user.CustomerRepository;
import com.stampcrush.backend.repository.user.OwnerRepository;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Profile({"local", "dev"})
@Transactional
@RequiredArgsConstructor
@Service
public class DevAuthService {

    private final AtomicLong idGen = new AtomicLong(-1);
    private final Map<String, Long> managerNameIdMap = new HashMap<>();
    private final Map<String, Long> visitorNameIdMap = new HashMap<>();

    private final OwnerRepository ownerRepository;
    private final CustomerRepository customerRepository;
    private final AuthTokensGenerator authTokensGenerator;

    public AuthTokensResponse joinManager(String nickname) {
        Owner owner = Owner.builder()
                .nickname(nickname)
                .oAuthProvider(OAuthProvider.NAVER)
                .oAuthId(idGen.decrementAndGet())
                .build();
        Owner savedOwner = ownerRepository.save(owner);
        Long ownerId = savedOwner.getId();
        managerNameIdMap.put(nickname, ownerId);
        return authTokensGenerator.generate(ownerId);
    }

    public AuthTokensResponse joinVisitor(String nickname) {
        Customer customer = Customer.registeredCustomerBuilder()
                .nickname(nickname)
                .email(nickname + "@test.com")
                .oAuthProvider(OAuthProvider.NAVER)
                .oAuthId(idGen.decrementAndGet())
                .build();
        Customer savedCustomer = customerRepository.save(customer);
        Long customerId = savedCustomer.getId();
        visitorNameIdMap.put(nickname, customerId);
        return authTokensGenerator.generate(customerId);
    }

    public AuthTokensResponse loginManger(String nickName) {
        Long id = managerNameIdMap.computeIfAbsent(nickName, (key) -> {
            joinManager(nickName);
            return managerNameIdMap.get(nickName);
        });
        return authTokensGenerator.generate(id);
    }

    public AuthTokensResponse loginVisitor(String nickName) {
        Long id = visitorNameIdMap.computeIfAbsent(nickName, (key) -> {
            joinVisitor(nickName);
            return visitorNameIdMap.get(nickName);
        });
        return authTokensGenerator.generate(id);
    }
}
