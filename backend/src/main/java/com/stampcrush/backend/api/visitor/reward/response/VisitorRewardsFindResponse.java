package com.stampcrush.backend.api.visitor.reward.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class VisitorRewardsFindResponse {

    private final List<VisitorRewardFindResponse> rewards;

    @Getter
    @RequiredArgsConstructor
    private static class VisitorRewardFindResponse {

        private final Long id;
        private final String rewardName;
        private final String cafeName;
        private final LocalDate createdAt;
        private final LocalDate usedAt;
    }
}
