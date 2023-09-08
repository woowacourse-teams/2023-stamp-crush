package com.stampcrush.backend.application.manager.customer;

import com.stampcrush.backend.entity.user.Customer;
import com.stampcrush.backend.exception.CustomerBadRequestException;
import com.stampcrush.backend.repository.user.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class ManagerCustomerCommandService {

    private final CustomerRepository customerRepository;

    public Long createTemporaryCustomer(String phoneNumber) {
        checkExistCustomer(phoneNumber);

        Customer customer = Customer.temporaryCustomerBuilder()
                .phoneNumber(phoneNumber)
                .build();

        Customer savedCustomer = customerRepository.save(customer);

        return savedCustomer.getId();
    }

    private void checkExistCustomer(String phoneNumber) {
        if (!customerRepository.findByPhoneNumber(phoneNumber).isEmpty()) {
            throw new CustomerBadRequestException("이미 존재하는 회원입니다");
        }
    }
}
