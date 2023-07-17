package com.stampcrush.backend.initializer;

import com.stampcrush.backend.entity.user.RegisterCustomer;
import com.stampcrush.backend.entity.user.TemporaryCustomer;
import com.stampcrush.backend.repository.user.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Profile("prod")
public class CustomerDataInitializer implements ApplicationRunner {

    private final CustomerRepository customerRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        TemporaryCustomer temporaryCustomer = new TemporaryCustomer("임시 고객", "01012345678");
        RegisterCustomer registerCustomer = new RegisterCustomer("가입 고객", "01098765432", "real", "pw");

        customerRepository.save(temporaryCustomer);
        customerRepository.save(registerCustomer);
    }
}
