package com.stampcrush.backend.application.reward;

import com.stampcrush.backend.application.reward.dto.RewardFind;
import com.stampcrush.backend.application.reward.dto.RewardFindResult;
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

import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class RewardService {

    private final RewardRepository rewardRepository;
    private final CustomerRepository customerRepository;
    private final CafeRepository cafeRepository;

    @Transactional(readOnly = true)
    public List<RewardFindResult> findRewards(RewardFind rewardFind) {
        List<Reward> rewards = rewardRepository.findAllByCustomerIdAndCafeIdAndUsed(rewardFind.getCustomerId(), rewardFind.getCafeId(), rewardFind.isUsed());
        return rewards.stream()
                .map(reward -> new RewardFindResult(reward.getId(), reward.getName()))
                .toList();
    }

    public void useReward(RewardUsedUpdate rewardUsedUpdate) {
        Reward reward = rewardRepository.findById(rewardUsedUpdate.getRewardId())
                .orElseThrow(IllegalArgumentException::new);
        Customer customer = customerRepository.findById(rewardUsedUpdate.getCustomerId())
                .orElseThrow(IllegalArgumentException::new);
        Cafe cafe = cafeRepository.findById(rewardUsedUpdate.getCafeId())
                .orElseThrow(IllegalArgumentException::new);
        reward.useReward(customer, cafe);
    }
}
