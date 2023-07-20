package com.stampcrush.backend.api.coupon.response;

import com.stampcrush.backend.application.coupon.dto.CustomerAccumulatingCouponFindResultDto;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static lombok.AccessLevel.PUBLIC;

@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor(access = PUBLIC)
@Getter
public class CustomerAccumulatingCouponFindResponse {

    private Long id;
    private Long customerId;
    private String nickname;
    private int stampCount;
    private LocalDateTime expireDate;
    private boolean isPrevious;

    public static CustomerAccumulatingCouponFindResponse from(CustomerAccumulatingCouponFindResultDto serviceDto) {
        return new CustomerAccumulatingCouponFindResponse(
                serviceDto.getId(),
                serviceDto.getCustomerId(),
                serviceDto.getNickname(),
                serviceDto.getStampCount(),
                serviceDto.getExpireDate(),
                serviceDto.isPrevious()
        );
    }
}
