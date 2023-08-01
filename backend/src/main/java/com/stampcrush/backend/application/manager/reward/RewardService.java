package com.stampcrush.backend.application.manager.reward;

import com.stampcrush.backend.application.manager.reward.dto.RewardFindDto;
import com.stampcrush.backend.application.manager.reward.dto.RewardFindResultDto;
import com.stampcrush.backend.application.manager.reward.dto.RewardUsedUpdateDto;
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
    public List<RewardFindResultDto> findRewards(RewardFindDto rewardFindDto) {
        List<Reward> rewards = rewardRepository.findAllByCustomerIdAndCafeIdAndUsed(rewardFindDto.getCustomerId(), rewardFindDto.getCafeId(), rewardFindDto.isUsed());
        return rewards.stream()
                .map(reward -> new RewardFindResultDto(reward.getId(), reward.getName()))
                .toList();
    }

    public void useReward(RewardUsedUpdateDto rewardUsedUpdateDto) {
        Reward reward = rewardRepository.findById(rewardUsedUpdateDto.getRewardId())
                .orElseThrow(IllegalArgumentException::new);
        Customer customer = customerRepository.findById(rewardUsedUpdateDto.getCustomerId())
                .orElseThrow(IllegalArgumentException::new);
        Cafe cafe = cafeRepository.findById(rewardUsedUpdateDto.getCafeId())
                .orElseThrow(IllegalArgumentException::new);
        reward.useReward(customer, cafe);
    }
}
