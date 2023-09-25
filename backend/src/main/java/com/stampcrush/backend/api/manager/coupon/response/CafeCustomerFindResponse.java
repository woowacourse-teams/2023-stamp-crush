package com.stampcrush.backend.api.manager.coupon.response;

import com.stampcrush.backend.application.manager.coupon.dto.CafeCustomerFindResultDto;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.format.DateTimeFormatter;

@EqualsAndHashCode
@RequiredArgsConstructor
@Getter
public class CafeCustomerFindResponse {

    private final Long id;
    private final String nickname;
    private final int stampCount;
    private final int rewardCount;
    private final int visitCount;
    private final int maxStampCount;
    private final String firstVisitDate;
    private final Boolean isRegistered;
    private final String recentVisitDate;

    public static CafeCustomerFindResponse from(CafeCustomerFindResultDto serviceDto) {
        return new CafeCustomerFindResponse(
                serviceDto.getId(),
                serviceDto.getNickname(),
                serviceDto.getStampCount(),
                serviceDto.getRewardCount(),
                serviceDto.getVisitCount(),
                serviceDto.getMaxStampCount(),
                serviceDto.getFirstVisitDate().format(DateTimeFormatter.ofPattern("yyyy:MM:dd")),
                serviceDto.isRegistered(),
                serviceDto.getRecentVisitDate().format(DateTimeFormatter.ofPattern("yyyy:MM:dd"))
        );
    }
}
