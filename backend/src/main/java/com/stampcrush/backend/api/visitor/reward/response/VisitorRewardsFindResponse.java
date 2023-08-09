package com.stampcrush.backend.api.visitor.reward.response;

import com.stampcrush.backend.application.visitor.reward.dto.VisitorRewardsFindResultDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class VisitorRewardsFindResponse {

    private final List<VisitorRewardFindResponse> rewards;

    public static VisitorRewardsFindResponse from(List<VisitorRewardsFindResultDto> dtos) {
        return new VisitorRewardsFindResponse(
                dtos.stream()
                        .map(VisitorRewardFindResponse::from)
                        .toList()
        );
    }

    @Getter
    @RequiredArgsConstructor
    public static class VisitorRewardFindResponse {

        private final Long id;
        private final String rewardName;
        private final String cafeName;
        private final LocalDate createdAt;
        private final LocalDate usedAt;

        public static VisitorRewardFindResponse from(VisitorRewardsFindResultDto dto) {
            LocalDate usedAt = null;
            if (dto.getUpdatedAt() != null) {
                usedAt = LocalDate.from(dto.getUpdatedAt());
            }
            return new VisitorRewardFindResponse(
                    dto.getId(),
                    dto.getRewardName(),
                    dto.getCafeName(),
                    LocalDate.from(dto.getCreatedAt()),
                    usedAt
            );
        }
    }
}