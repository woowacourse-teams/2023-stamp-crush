package com.stampcrush.backend.api.manager.coupon.response;

import com.stampcrush.backend.application.manager.coupon.dto.CafeCustomerFindResultDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.format.DateTimeFormatter;

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
    private String firstVisitDate;
    private Boolean isRegistered;

    public static CafeCustomerFindResponse from(CafeCustomerFindResultDto serviceDto) {
        return new CafeCustomerFindResponse(
                serviceDto.getId(),
                serviceDto.getNickname(),
                serviceDto.getStampCount(),
                serviceDto.getRewardCount(),
                serviceDto.getVisitCount(),
                serviceDto.getMaxStampCount(),
                serviceDto.getFirstVisitDate().format(DateTimeFormatter.ofPattern("yyyy:MM:dd")),
                serviceDto.isRegistered()
        );
    }
}
