package com.stampcrush.backend.application.visitor.reward.dto;

import com.stampcrush.backend.entity.reward.Reward;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class VisitorRewardsFindResultDto {

    private final Long id;
    private final String rewardName;
    private final String cafeName;
    private final LocalDate createdAt;
    private final LocalDate updatedAt;

    public static VisitorRewardsFindResultDto from(Reward reward) {
        LocalDateTime updatedAt = reward.getUpdatedAt();
        LocalDate usedAt = null;
        if (updatedAt != null) {
            usedAt = LocalDate.from(updatedAt);
        }
        return new VisitorRewardsFindResultDto(
                reward.getId(),
                reward.getName(),
                reward.getCafe().getName(),
                LocalDate.from(reward.getCreatedAt()),
                usedAt
        );
    }
}
