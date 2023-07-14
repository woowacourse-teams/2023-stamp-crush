package com.stampcrush.backend.repository.reward;

import com.stampcrush.backend.entity.reward.Reward;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RewardRepository extends JpaRepository<Reward, Long> {
}
