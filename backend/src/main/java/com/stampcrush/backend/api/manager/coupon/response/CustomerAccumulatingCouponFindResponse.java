package com.stampcrush.backend.api.manager.coupon.response;

import com.stampcrush.backend.application.manager.coupon.dto.CustomerAccumulatingCouponFindResultDto;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.format.DateTimeFormatter;

@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CustomerAccumulatingCouponFindResponse {

    private Long id;
    private Long customerId;
    private String nickname;
    private int stampCount;
    private String expireDate;
    private Boolean isPrevious;
    private int maxStampCount;

    public static CustomerAccumulatingCouponFindResponse from(CustomerAccumulatingCouponFindResultDto serviceDto) {
        return new CustomerAccumulatingCouponFindResponse(
                serviceDto.getId(),
                serviceDto.getCustomerId(),
                serviceDto.getNickname(),
                serviceDto.getStampCount(),
                serviceDto.getExpireDate().format(DateTimeFormatter.ofPattern("yyyy:MM:dd")),
                serviceDto.isPrevious(),
                serviceDto.getMaxStampCount()
        );
    }
}
