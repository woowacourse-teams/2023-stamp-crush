package com.stampcrush.backend.api.coupon.response;

import com.stampcrush.backend.application.coupon.dto.CustomerAccumulatingCouponFindResultDto;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CustomerAccumulatingCouponFindResponse {

    private Long id;
    private Long customerId;
    private String nickname;
    private int stampCount;
    private LocalDateTime expireDate;
    private boolean isPrevious;
    private int maxStampCount;

    public static CustomerAccumulatingCouponFindResponse from(CustomerAccumulatingCouponFindResultDto serviceDto) {
        return new CustomerAccumulatingCouponFindResponse(
                serviceDto.getId(),
                serviceDto.getCustomerId(),
                serviceDto.getNickname(),
                serviceDto.getStampCount(),
                serviceDto.getExpireDate(),
                serviceDto.isPrevious(),
                10
        );
    }
}
