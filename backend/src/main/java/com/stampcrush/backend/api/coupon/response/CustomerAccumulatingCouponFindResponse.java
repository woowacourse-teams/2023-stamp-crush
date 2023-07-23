package com.stampcrush.backend.api.coupon.response;

import com.fasterxml.jackson.annotation.JsonFormat;
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

    @JsonFormat(pattern = "yyyy:MM:dd")
    private LocalDateTime expireDate;
    private Boolean isPrevious;
    private int maxStampCount;

    public static CustomerAccumulatingCouponFindResponse from(CustomerAccumulatingCouponFindResultDto serviceDto) {
        return new CustomerAccumulatingCouponFindResponse(
                serviceDto.getId(),
                serviceDto.getCustomerId(),
                serviceDto.getNickname(),
                serviceDto.getStampCount(),
                serviceDto.getExpireDate(),
                serviceDto.isPrevious(),
                serviceDto.getMaxStampCount()
        );
    }
}
