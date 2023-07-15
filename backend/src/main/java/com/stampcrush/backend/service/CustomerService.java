package com.stampcrush.backend.service;

import com.stampcrush.backend.entity.user.Customer;
import com.stampcrush.backend.repository.user.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

//    리스트가 아니라 CustomerResponse 를 반환하고 존재하지 않을 때는 인증되지 않았다는 에러를 내려줘야 하는것이 아닐지
//    public List<CustomerResponse> findCustomer(String phoneNumber) {
//        try {
//            Customer customer = customerRepository.findByphoneNumber(phoneNumber)
//                    .orElseThrow(() -> new IllegalArgumentException("해당 번호로 조회되는 고객이 없습니다"));
//            return List.of(CustomerResponse.from(customer));
//        } catch (IllegalArgumentException e) {
//            return Collections.emptyList();
//        }
//    }

    public List<CustomerResponse> findCustomer(String phoneNumber) {
        try {
            List<Customer> customers = customerRepository.findByphoneNumber(phoneNumber);
            return customers.stream().map(CustomerResponse::from).collect(Collectors.toList());
        } catch (IllegalArgumentException e) {
            return Collections.emptyList();
        }
    }
}
