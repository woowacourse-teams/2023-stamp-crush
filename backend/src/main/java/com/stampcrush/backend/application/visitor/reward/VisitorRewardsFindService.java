package com.stampcrush.backend.application.visitor.reward;

import com.stampcrush.backend.application.visitor.reward.dto.VisitorRewardsFindResultDto;
import com.stampcrush.backend.entity.reward.Reward;
import com.stampcrush.backend.entity.user.Customer;
import com.stampcrush.backend.exception.CustomerNotFoundException;
import com.stampcrush.backend.repository.reward.RewardRepository;
import com.stampcrush.backend.repository.user.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional
public class VisitorRewardsFindService {

    private final RewardRepository rewardRepository;
    private final CustomerRepository customerRepository;

    public List<VisitorRewardsFindResultDto> findRewards(Long customerId, Boolean used) {
        Customer customer = findExistingCustomer(customerId);
        List<Reward> findRewards = rewardRepository.findAllByCustomerAndUsed(customer, used);
        return findRewards.stream()
                .map(VisitorRewardsFindResultDto::from)
                .toList();
    }

    private Customer findExistingCustomer(Long customerId) {
        Optional<Customer> customer = customerRepository.findById(customerId);
        if (customer.isEmpty()) {
            throw new CustomerNotFoundException("해당 id를 가진 고객을 찾을 수 없습니다.");
        }

        return customer.get();
    }
}
