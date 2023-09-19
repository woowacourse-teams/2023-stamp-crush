package com.stampcrush.backend.application.manager.reward;

import com.stampcrush.backend.application.manager.reward.dto.RewardFindDto;
import com.stampcrush.backend.application.manager.reward.dto.RewardFindResultDto;
import com.stampcrush.backend.entity.cafe.Cafe;
import com.stampcrush.backend.entity.reward.Reward;
import com.stampcrush.backend.entity.user.Owner;
import com.stampcrush.backend.entity.visithistory.VisitHistory;
import com.stampcrush.backend.exception.CafeNotFoundException;
import com.stampcrush.backend.exception.OwnerNotFoundException;
import com.stampcrush.backend.exception.OwnerUnAuthorizationException;
import com.stampcrush.backend.repository.cafe.CafeRepository;
import com.stampcrush.backend.repository.reward.RewardRepository;
import com.stampcrush.backend.repository.user.OwnerRepository;
import com.stampcrush.backend.repository.visithistory.VisitHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ManagerRewardFindService {

    private final RewardRepository rewardRepository;
    private final CafeRepository cafeRepository;
    private final OwnerRepository ownerRepository;
    private final VisitHistoryRepository visitHistoryRepository;

    public List<RewardFindResultDto> findRewards(Long ownerId, RewardFindDto rewardFindDto) {
        List<VisitHistory> visitHistories = visitHistoryRepository.findByCafeIdAndCustomerId(rewardFindDto.getCafeId(), rewardFindDto.getCustomerId());

        if (visitHistories.isEmpty()) {
            throw new OwnerUnAuthorizationException("카페의 고객이 아닙니다");
        }

        Cafe cafe = cafeRepository.findById(rewardFindDto.getCafeId())
                .orElseThrow(() -> new CafeNotFoundException("카페를 찾지 못했습니다."));
        Owner owner = ownerRepository.findById(ownerId)
                .orElseThrow(() -> new OwnerNotFoundException("사장을 찾지 못했습니다."));
        cafe.validateOwnership(owner);
        List<Reward> rewards = rewardRepository.findAllByCustomerIdAndCafeIdAndUsed(rewardFindDto.getCustomerId(), rewardFindDto.getCafeId(), rewardFindDto.isUsed());
        return rewards.stream()
                .map(reward -> new RewardFindResultDto(reward.getId(), reward.getName()))
                .toList();
    }
}
