package com.stampcrush.backend.application.reward;

import com.stampcrush.backend.application.reward.dto.RewardUsedUpdate;
import com.stampcrush.backend.entity.cafe.Cafe;
import com.stampcrush.backend.entity.reward.Reward;
import com.stampcrush.backend.entity.user.Customer;
import com.stampcrush.backend.repository.cafe.CafeRepository;
import com.stampcrush.backend.repository.reward.RewardRepository;
import com.stampcrush.backend.repository.user.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class RewardService {

    private final RewardRepository rewardRepository;
    private final CustomerRepository customerRepository;
    private final CafeRepository cafeRepository;

    public void updateUsed(RewardUsedUpdate rewardUsedUpdate) {
        Reward reward = rewardRepository.findById(rewardUsedUpdate.getRewardId())
                .orElseThrow(IllegalArgumentException::new);
        Customer customer = customerRepository.findById(rewardUsedUpdate.getCustomerId())
                .orElseThrow(IllegalArgumentException::new);
        Cafe cafe = cafeRepository.findById(rewardUsedUpdate.getCafeId())
                .orElseThrow(IllegalArgumentException::new);
        reward.useReward(customer, cafe);
    }
}
