package com.stampcrush.backend.application.manager.reward;

import com.stampcrush.backend.application.manager.reward.dto.RewardUsedUpdateDto;
import com.stampcrush.backend.entity.cafe.Cafe;
import com.stampcrush.backend.entity.reward.Reward;
import com.stampcrush.backend.entity.user.Customer;
import com.stampcrush.backend.entity.user.Owner;
import com.stampcrush.backend.exception.OwnerNotFoundException;
import com.stampcrush.backend.repository.cafe.CafeRepository;
import com.stampcrush.backend.repository.reward.RewardRepository;
import com.stampcrush.backend.repository.user.CustomerRepository;
import com.stampcrush.backend.repository.user.OwnerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class ManagerRewardCommandService {

    private final RewardRepository rewardRepository;
    private final CustomerRepository customerRepository;
    private final CafeRepository cafeRepository;
    private final OwnerRepository ownerRepository;

    public void useReward(Long ownerId, RewardUsedUpdateDto rewardUsedUpdateDto) {
        Reward reward = rewardRepository.findById(rewardUsedUpdateDto.getRewardId())
                .orElseThrow(IllegalArgumentException::new);
        Customer customer = customerRepository.findById(rewardUsedUpdateDto.getCustomerId())
                .orElseThrow(IllegalArgumentException::new);
        Cafe cafe = cafeRepository.findById(rewardUsedUpdateDto.getCafeId())
                .orElseThrow(IllegalArgumentException::new);
        Owner owner = ownerRepository.findById(ownerId)
                .orElseThrow(() -> new OwnerNotFoundException("회원가입을 먼저 진행해주세요"));

        cafe.validateOwnership(owner);

        reward.useReward(customer, cafe);
    }
}
