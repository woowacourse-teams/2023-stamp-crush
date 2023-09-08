package com.stampcrush.backend.application.visitor.profile;

import com.stampcrush.backend.application.visitor.profile.dto.VisitorProfileFindByPhoneNumberResultDto;
import com.stampcrush.backend.application.visitor.profile.dto.VisitorProfileFindResultDto;
import com.stampcrush.backend.entity.user.Customer;
import com.stampcrush.backend.exception.CustomerNotFoundException;
import com.stampcrush.backend.exception.StampCrushException;
import com.stampcrush.backend.repository.user.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class VisitorProfilesFindService {

    private final CustomerRepository customerRepository;

    public VisitorProfileFindResultDto findVisitorProfile(Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException("고객을 찾을 수 없습니다"));

        return new VisitorProfileFindResultDto(customer.getId(), customer.getNickname(), customer.getPhoneNumber(), customer.getEmail());
    }

    public VisitorProfileFindByPhoneNumberResultDto findCustomerProfileByNumber(String phoneNumber) {
        Customer customer = findCustomerWithSamePhoneNumber(phoneNumber);
        return VisitorProfileFindByPhoneNumberResultDto.from(customer);
    }

    private Customer findCustomerWithSamePhoneNumber(String phoneNumber) {
        List<Customer> customers = customerRepository.findByPhoneNumber(phoneNumber);
        if (customers.isEmpty()) {
            return null;
        }
        if (customers.size() > 1) {
            throw new StampCrushException("🚨 전화번호 " + phoneNumber + "에 대해서 중복되는 사용자가 이미 2명 이상 존재합니다.");
        }

        return customers.get(0);
    }
}
