package com.stampcrush.backend.application.coupon.dto;

import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Objects;

@ToString
@Getter
public class CafeCustomerInfoResponseDto {

    private final Long id;
    private final String nickname;
    private final int stampCount;
    private final int rewardCount;
    private final int visitCount;
    private final LocalDateTime firstVisitDate;

    private final boolean isRegistered;

    public CafeCustomerInfoResponseDto(Long id,
                                       String nickname,
                                       int stampCount,
                                       int rewardCount,
                                       int visitCount,
                                       LocalDateTime firstVisitDate,
                                       boolean isRegistered) {
        this.id = id;
        this.nickname = nickname;
        this.stampCount = stampCount;
        this.rewardCount = rewardCount;
        this.visitCount = visitCount;
        this.firstVisitDate = firstVisitDate;
        this.isRegistered = isRegistered;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CafeCustomerInfoResponseDto that = (CafeCustomerInfoResponseDto) o;
        return stampCount == that.stampCount && rewardCount == that.rewardCount && visitCount == that.visitCount && isRegistered == that.isRegistered && Objects.equals(id, that.id) && Objects.equals(nickname, that.nickname) && Objects.equals(firstVisitDate, that.firstVisitDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nickname, stampCount, rewardCount, visitCount, firstVisitDate, isRegistered);
    }
}
