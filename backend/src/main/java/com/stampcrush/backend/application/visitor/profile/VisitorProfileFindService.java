package com.stampcrush.backend.application.visitor.profile;

import com.stampcrush.backend.application.visitor.profile.dto.VisitorPhoneNumberFindResultDto;
import com.stampcrush.backend.entity.user.Customer;
import com.stampcrush.backend.exception.CustomerNotFoundException;
import com.stampcrush.backend.repository.user.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class VisitorProfileFindService {

    private CustomerRepository customerRepository;

    public VisitorPhoneNumberFindResultDto findPhoneNumber(Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException("고객이 존재하지 않습니다"));

        String phoneNumber = customer.getPhoneNumber();
        return new VisitorPhoneNumberFindResultDto(phoneNumber);
    }
}
