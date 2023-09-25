package com.stampcrush.backend.api.visitor.profile;

import com.stampcrush.backend.entity.user.Customer;
import com.stampcrush.backend.exception.CustomerNotFoundException;
import com.stampcrush.backend.repository.user.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class VisitorCancelMembershipService {

    private final CustomerRepository customerRepository;

    public void cancelMembership(Long customerId) {
        // TODO: 회원 탈퇴 기능 구현
        Customer customer = findExistingCustomer(customerId);
        customerRepository.delete(customer);
    }

    @NotNull
    private Customer findExistingCustomer(Long customerId) {
        Optional<Customer> customer = customerRepository.findById(customerId);
        if (customer.isEmpty()) {
            throw new CustomerNotFoundException("해당 id: " + customerId + "에 해당하는 고객이 존재하지 않습니다.");
        }
        return customer.get();
    }
}
