package com.stampcrush.backend.application.visitor.reward.dto;

import com.stampcrush.backend.entity.reward.Reward;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Getter
@RequiredArgsConstructor
public class VisitorRewardsFindResultDto {

    private final Long id;
    private final String rewardName;
    private final String cafeName;
    private final LocalDate createdAt;
    private final LocalDate updatedAt;

    public static VisitorRewardsFindResultDto from(Reward reward) {
        return new VisitorRewardsFindResultDto(
                reward.getId(),
                reward.getName(),
                reward.getCafe().getName(),
                LocalDate.from(reward.getCreatedAt()),
                LocalDate.from(reward.getUpdatedAt())
        );
    }
}
