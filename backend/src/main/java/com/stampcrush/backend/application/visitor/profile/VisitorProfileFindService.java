package com.stampcrush.backend.application.visitor.profile;

import com.stampcrush.backend.application.visitor.profile.dto.VisitorPhoneNumberFindResultDto;
import com.stampcrush.backend.application.visitor.profile.dto.VisitorProfileFindResultDto;
import com.stampcrush.backend.entity.user.RegisterCustomer;
import com.stampcrush.backend.exception.CustomerNotFoundException;
import com.stampcrush.backend.repository.user.RegisterCustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class VisitorProfileFindService {

    private RegisterCustomerRepository registerCustomerRepository;

    public VisitorPhoneNumberFindResultDto findPhoneNumber(Long customerId) {
        // TODO: customerId 왜 int 로 변환해야 하는지 모르겠습니다
        RegisterCustomer customer = registerCustomerRepository.findById(Math.toIntExact(customerId))
                .orElseThrow(() -> new CustomerNotFoundException("고객이 존재하지 않습니다"));

        String phoneNumber = customer.getPhoneNumber();
        return new VisitorPhoneNumberFindResultDto(phoneNumber);
    }

    public VisitorProfileFindResultDto findProfile(Long customerId) {
        RegisterCustomer customer = registerCustomerRepository.findById(Math.toIntExact(customerId))
                .orElseThrow(() -> new CustomerNotFoundException("고객이 존재하지 않습니다"));

        return new VisitorProfileFindResultDto(customer.getId(), customer.getNickname(), customer.getPhoneNumber(), customer.getEmail());
    }
}
