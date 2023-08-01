package com.stampcrush.backend.api.manager.coupon.response;

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
    private int maxStampCount;

    @JsonFormat(pattern = "yyyy:MM:dd")
    private LocalDateTime firstVisitDate;
    private Boolean isRegistered;

    public static CafeCustomerFindResponse from(CafeCustomerFindResultDto serviceDto) {
        return new CafeCustomerFindResponse(
                serviceDto.getId(),
                serviceDto.getNickname(),
                serviceDto.getStampCount(),
                serviceDto.getRewardCount(),
                serviceDto.getVisitCount(),
                serviceDto.getMaxStampCount(),
                serviceDto.getFirstVisitDate(),
                serviceDto.isRegistered()
        );
    }
}
