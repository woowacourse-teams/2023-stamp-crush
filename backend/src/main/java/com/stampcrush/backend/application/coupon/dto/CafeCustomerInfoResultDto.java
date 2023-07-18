package com.stampcrush.backend.application.coupon.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString
@EqualsAndHashCode
@Getter
public class CafeCustomerInfoResultDto {

    private final Long id;
    private final String nickname;
    private final int stampCount;
    private final int rewardCount;
    private final int visitCount;
    private final LocalDateTime firstVisitDate;

    private final boolean isRegistered;

    public CafeCustomerInfoResultDto(Long id,
                                     String nickname,
                                     int stampCount,
                                     int rewardCount,
                                     int visitCount,
                                     LocalDateTime firstVisitDate,
                                     boolean isRegistered) {
        this.id = id;
        this.nickname = nickname;
        this.stampCount = stampCount;
        this.rewardCount = rewardCount;
        this.visitCount = visitCount;
        this.firstVisitDate = firstVisitDate;
        this.isRegistered = isRegistered;
    }
}
