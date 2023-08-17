package com.stampcrush.backend.application.visitor.profile;

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
public class VisitorProfilesFindService {

    private RegisterCustomerRepository registerCustomerRepository;

    public VisitorProfileFindResultDto findVisitorProfile(Long customerId) {
        RegisterCustomer customer = registerCustomerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException("고객을 찾을 수 없습니다"));

        return new VisitorProfileFindResultDto(customer.getId(), customer.getNickname(), customer.getPhoneNumber(), customer.getEmail());
    }
}
