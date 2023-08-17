package com.stampcrush.backend.application.manager.reward;

import com.stampcrush.backend.application.manager.reward.dto.RewardFindDto;
import com.stampcrush.backend.application.manager.reward.dto.RewardFindResultDto;
import com.stampcrush.backend.entity.reward.Reward;
import com.stampcrush.backend.repository.reward.RewardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ManagerRewardFindService {

    private final RewardRepository rewardRepository;

    public List<RewardFindResultDto> findRewards(RewardFindDto rewardFindDto) {
        List<Reward> rewards = rewardRepository.findAllByCustomerIdAndCafeIdAndUsed(rewardFindDto.getCustomerId(), rewardFindDto.getCafeId(), rewardFindDto.isUsed());
        return rewards.stream()
                .map(reward -> new RewardFindResultDto(reward.getId(), reward.getName()))
                .toList();
    }
}
