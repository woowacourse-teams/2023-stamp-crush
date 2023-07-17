package com.stampcrush.backend.application.coupon.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
@Getter
public class CafeCustomerInfoResultDto {

    private final Long id;
    private final String nickname;
    private final int stampCount;
    private final int rewardCount;
    private final int visitCount;
    private final LocalDateTime firstVisitDate;

    private final boolean isRegistered;
}
