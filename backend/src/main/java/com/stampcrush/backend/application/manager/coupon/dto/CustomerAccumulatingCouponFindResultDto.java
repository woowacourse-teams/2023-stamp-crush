package com.stampcrush.backend.application.manager.coupon.dto;

import com.stampcrush.backend.entity.coupon.Coupon;
import com.stampcrush.backend.entity.user.Customer;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static lombok.AccessLevel.PROTECTED;

@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
@Getter
public class CustomerAccumulatingCouponFindResultDto {

    private Long id;
    private Long customerId;
    private String nickname;
    private int stampCount;
    private LocalDateTime expireDate;
    private boolean isPrevious;
    private int maxStampCount;

    public static CustomerAccumulatingCouponFindResultDto of(Coupon coupon, Customer customer, boolean isPrevious) {
        return new CustomerAccumulatingCouponFindResultDto(
                coupon.getId(),
                customer.getId(),
                customer.getNickname(),
                coupon.getStampCount(),
                coupon.calculateExpireDate(),
                isPrevious,
                coupon.getCouponMaxStampCount()
        );
    }
}
