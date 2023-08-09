package com.stampcrush.backend.application.visitor.reward.dto;

import com.stampcrush.backend.entity.reward.Reward;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class VisitorRewardsFindResultDto {

    private final Long id;
    private final String rewardName;
    private final String cafeName;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public static VisitorRewardsFindResultDto from(Reward reward) {
        return new VisitorRewardsFindResultDto(
                reward.getId(),
                reward.getName(),
                reward.getCafe().getName(),
                reward.getCreatedAt(),
                reward.getUpdatedAt()
        );
    }
}
