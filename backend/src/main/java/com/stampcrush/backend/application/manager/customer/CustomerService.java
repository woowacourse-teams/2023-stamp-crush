package com.stampcrush.backend.application.manager.customer;

import com.stampcrush.backend.application.manager.customer.dto.CustomerFindDto;
import com.stampcrush.backend.application.manager.customer.dto.CustomersFindResultDto;
import com.stampcrush.backend.entity.user.Customer;
import com.stampcrush.backend.entity.user.TemporaryCustomer;
import com.stampcrush.backend.exception.CustomerBadRequestException;
import com.stampcrush.backend.repository.user.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
@Service
public class CustomerService {

    private static final int NICKNAME_LENGTH = 4;

    private final CustomerRepository customerRepository;

    @Transactional(readOnly = true)
    public CustomersFindResultDto findCustomer(String phoneNumber) {
        List<Customer> customers = customerRepository.findByPhoneNumber(phoneNumber);

        List<CustomerFindDto> customerFindDtos = customers.stream()
                .map(CustomerFindDto::from)
                .collect(toList());

        return new CustomersFindResultDto(customerFindDtos);
    }

    @Transactional
    public Long createTemporaryCustomer(String phoneNumber) {
        checkExistCustomer(phoneNumber);

        TemporaryCustomer temporaryCustomer = TemporaryCustomer.from(phoneNumber);
        TemporaryCustomer savedTemporaryCustomer = customerRepository.save(temporaryCustomer);

        return savedTemporaryCustomer.getId();
    }

    private void checkExistCustomer(String phoneNumber) {
        if (!customerRepository.findByPhoneNumber(phoneNumber).isEmpty()) {
            throw new CustomerBadRequestException("이미 존재하는 회원입니다");
        }
    }
}
