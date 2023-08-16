package com.stampcrush.backend.application.visitor.profile;

import com.stampcrush.backend.entity.user.Customer;
import com.stampcrush.backend.exception.CustomerNotFoundException;
import com.stampcrush.backend.exception.DuplicatePhoneNumberException;
import com.stampcrush.backend.repository.user.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Transactional
@Service
public class VisitorProfilesCommandService {

    private final CustomerRepository customerRepository;

    public void registerPhoneNumber(Long customerId, String phoneNumber) {
        try {
            Customer customer = findExistingCustomer(customerId);
            customer.registerPhoneNumber(phoneNumber);
        } catch (DataIntegrityViolationException exception) {
            throw new DuplicatePhoneNumberException("이미 등록된 전화번호입니다.", exception);
        }
    }

    private Customer findExistingCustomer(Long customerId) {
        Optional<Customer> findCustomer = customerRepository.findById(customerId);

        if (findCustomer.isEmpty()) {
            throw new CustomerNotFoundException("해당 아이디의 고객을 찾을 수 없습니다.");
        }

        return findCustomer.get();
    }
}
