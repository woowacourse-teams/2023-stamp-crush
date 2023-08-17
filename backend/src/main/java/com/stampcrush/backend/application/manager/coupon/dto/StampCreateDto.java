package com.stampcrush.backend.application.manager.coupon.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class StampCreateDto {

    private Long ownerId;
    private Long customerId;
    private Long couponId;
    private Integer earningStampCount;
}
