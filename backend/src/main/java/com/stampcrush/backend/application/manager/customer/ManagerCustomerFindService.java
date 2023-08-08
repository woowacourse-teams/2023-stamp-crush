package com.stampcrush.backend.application.manager.customer;

import com.stampcrush.backend.application.manager.customer.dto.CustomerFindDto;
import com.stampcrush.backend.entity.user.Customer;
import com.stampcrush.backend.repository.user.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ManagerCustomerFindService {

    private final CustomerRepository customerRepository;

    @Transactional(readOnly = true)
    public List<CustomerFindDto> findCustomer(String phoneNumber) {
        List<Customer> customers = customerRepository.findByPhoneNumber(phoneNumber);

        List<CustomerFindDto> customerFindDtos = customers.stream()
                .map(CustomerFindDto::from)
                .collect(toList());

        return customerFindDtos;
    }
}
