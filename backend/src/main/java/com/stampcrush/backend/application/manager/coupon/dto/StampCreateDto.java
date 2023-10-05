package com.stampcrush.backend.application.manager.coupon.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class StampCreateDto {

    private final Long ownerId;
    private final Long customerId;
    private final Long couponId;
    private final Integer earningStampCount;
}
