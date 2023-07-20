package com.stampcrush.backend.api.coupon.response;

import com.stampcrush.backend.application.coupon.dto.CafeCustomerFindResultDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CafeCustomerFindResponse {

    private Long id;
    private String nickname;
    private int stampCount;
    private int rewardCount;
    private int visitCount;
    private int maxStampCount;
    private LocalDateTime firstVisitDate;
    private boolean isRegistered;

    public static CafeCustomerFindResponse from(CafeCustomerFindResultDto serviceDto) {
        return new CafeCustomerFindResponse(
                serviceDto.getId(),
                serviceDto.getNickname(),
                serviceDto.getStampCount(),
                serviceDto.getRewardCount(),
                serviceDto.getVisitCount(),
                10,
                serviceDto.getFirstVisitDate(),
                serviceDto.isRegistered()
        );
    }
}
