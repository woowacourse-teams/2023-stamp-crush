package com.stampcrush.backend.api.visitor.reward.response;

import com.stampcrush.backend.application.visitor.reward.dto.VisitorRewardsFindResultDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class VisitorRewardsFindResponse {

    private List<VisitorRewardFindResponse> rewards;

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
        private final String createdAt;
        private final String usedAt;

        public static VisitorRewardFindResponse from(VisitorRewardsFindResultDto dto) {
            return new VisitorRewardFindResponse(
                    dto.getId(),
                    dto.getRewardName(),
                    dto.getCafeName(),
                    LocalDate.from(dto.getCreatedAt()).format(DateTimeFormatter.ofPattern("yyyy:MM:dd")),
                    LocalDate.from(dto.getUpdatedAt()).format(DateTimeFormatter.ofPattern("yyyy:MM:dd"))
            );
        }
    }
}
