package com.stampcrush.backend.repository.reward;

import com.stampcrush.backend.entity.reward.Reward;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RewardRepository extends JpaRepository<Reward, Long> {

    List<Reward> findAllByCustomerIdAndCafeIdAndUsed(Long CustomerId, Long CafeId, boolean used);
}
