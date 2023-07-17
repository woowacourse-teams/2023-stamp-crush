package com.stampcrush.backend.application.customer;

import com.stampcrush.backend.api.customer.response.CustomerFindResponse;
import com.stampcrush.backend.api.customer.response.CustomersFindResponse;
import com.stampcrush.backend.entity.user.Customer;
import com.stampcrush.backend.entity.user.TemporaryCustomer;
import com.stampcrush.backend.repository.user.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@AllArgsConstructor
@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomersFindResponse findCustomer(String phoneNumber) {
        List<Customer> customers = customerRepository.findByphoneNumber(phoneNumber);

        return new CustomersFindResponse(customers.stream()
                .map(CustomerFindResponse::from)
                .collect(toList()));
    }

    public Long createTemporaryCustomer(String phoneNumber) {
        if (!customerRepository.findByphoneNumber(phoneNumber).isEmpty()) {
            throw new IllegalArgumentException("이미 존재하는 회원입니다");
        }

        String nickname = phoneNumber.substring(phoneNumber.length() - 4);
        TemporaryCustomer temporaryCustomer = new TemporaryCustomer(nickname, phoneNumber);

        TemporaryCustomer savedTemporaryCustomer = customerRepository.save(temporaryCustomer);

        return savedTemporaryCustomer.getId();
    }
}
