package com.stampcrush.backend.application.coupon.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDateTime;

@EqualsAndHashCode
@Getter
public class CustomerUsingCouponResponseDto {

    private final Long id;
    private final Long customerId;
    private final String nickname;
    private final int stampCount;

    @JsonFormat(pattern = "yyyy:MM:dd")
    private final LocalDateTime expireDate;
    private final boolean isPrevious;

    public CustomerUsingCouponResponseDto(Long id,
                                          Long customerId,
                                          String nickname,
                                          int stampCount,
                                          LocalDateTime expireDate,
                                          boolean isPrevious
    ) {
        this.id = id;
        this.customerId = customerId;
        this.nickname = nickname;
        this.stampCount = stampCount;
        this.expireDate = expireDate;
        this.isPrevious = isPrevious;
    }
}
