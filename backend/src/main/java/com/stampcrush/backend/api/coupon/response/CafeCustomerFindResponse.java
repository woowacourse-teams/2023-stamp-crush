package com.stampcrush.backend.api.coupon.response;

import com.fasterxml.jackson.annotation.JsonFormat;
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

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime firstVisitDate;
    private boolean isRegistered;

    public static CafeCustomerFindResponse from(CafeCustomerFindResultDto serviceDto) {
        return new CafeCustomerFindResponse(
                serviceDto.getId(),
                serviceDto.getNickname(),
                serviceDto.getStampCount(),
                serviceDto.getRewardCount(),
                serviceDto.getVisitCount(),
                serviceDto.getFirstVisitDate(),
                serviceDto.isRegistered()
        );
    }
}
