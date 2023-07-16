package com.stampcrush.backend.application.coupon.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerUsingCouponResponseDto that = (CustomerUsingCouponResponseDto) o;
        return getStampCount() == that.getStampCount() && isPrevious() == that.isPrevious() && Objects.equals(getId(), that.getId()) && Objects.equals(getCustomerId(), that.getCustomerId()) && Objects.equals(getNickname(), that.getNickname()) && Objects.equals(getExpireDate(), that.getExpireDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getCustomerId(), getNickname(), getStampCount(), getExpireDate(), isPrevious());
    }
}
