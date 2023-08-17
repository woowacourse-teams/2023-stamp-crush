package com.stampcrush.backend.application.manager.coupon.dto;

import com.stampcrush.backend.application.manager.coupon.CustomerCouponStatistics;
import com.stampcrush.backend.entity.user.Customer;
import lombok.*;

import java.time.LocalDateTime;

@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@Getter
public class CafeCustomerFindResultDto {

    private Long id;
    private String nickname;
    private int stampCount;
    private int rewardCount;
    private int visitCount;
    private LocalDateTime firstVisitDate;
    private boolean isRegistered;
    private int maxStampCount;

    public static CafeCustomerFindResultDto of(Customer customer, CustomerCouponStatistics customerCouponStatistics,
                                               int visitCount, LocalDateTime firstVisitDate) {
        return new CafeCustomerFindResultDto(
                customer.getId(),
                customer.getNickname(),
                customerCouponStatistics.getStampCount(),
                customerCouponStatistics.getRewardCount(),
                visitCount,
                firstVisitDate,
                customer.isRegistered(),
                customerCouponStatistics.getMaxStampCount()
        );
    }
}
