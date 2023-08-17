package com.stampcrush.backend.application.visitor.visithistory;

import com.stampcrush.backend.application.visitor.visithistory.dto.CustomerStampHistoryFindResultDto;
import com.stampcrush.backend.entity.user.Customer;
import com.stampcrush.backend.entity.visithistory.VisitHistory;
import com.stampcrush.backend.exception.CustomerNotFoundException;
import com.stampcrush.backend.repository.user.CustomerRepository;
import com.stampcrush.backend.repository.visithistory.VisitHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class VisitorVisitHistoryFindService {

    private final VisitHistoryRepository visitHistoryRepository;
    private final CustomerRepository customerRepository;

    public List<CustomerStampHistoryFindResultDto> findStampHistoriesByCustomer(Long customerId) {
        Customer customer = findCustomerById(customerId);

        List<VisitHistory> visitHistories = visitHistoryRepository.findVisitHistoriesByCustomer(customer);
        return visitHistories.stream()
                .map(CustomerStampHistoryFindResultDto::from)
                .toList();
    }

    private Customer findCustomerById(Long customerId) {
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException("존재하지 않는 고객입니다."));
    }
}
