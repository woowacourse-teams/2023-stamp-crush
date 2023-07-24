package com.stampcrush.backend.application.coupon.dto;

import com.stampcrush.backend.entity.coupon.Coupon;
import com.stampcrush.backend.entity.user.Customer;
import lombok.*;

import java.time.LocalDateTime;

import static lombok.AccessLevel.PUBLIC;

@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor(access = PUBLIC)
@Getter
public class CustomerAccumulatingCouponFindResultDto {

    private Long id;
    private Long customerId;
    private String nickname;
    private int stampCount;
    private LocalDateTime expireDate;
    private boolean isPrevious;
    private int maxStampCount;

    public static CustomerAccumulatingCouponFindResultDto from(Coupon coupon, Customer customer, boolean isPrevious) {
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
