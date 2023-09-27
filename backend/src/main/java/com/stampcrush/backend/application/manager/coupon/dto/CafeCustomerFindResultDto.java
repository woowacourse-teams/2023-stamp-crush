package com.stampcrush.backend.application.manager.coupon.dto;

import com.stampcrush.backend.application.manager.coupon.CustomerCouponStatistics;
import com.stampcrush.backend.entity.user.Customer;
import com.stampcrush.backend.entity.visithistory.VisitHistories;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
    private LocalDateTime recentVisitDate;

    public static CafeCustomerFindResultDto of(Customer customer, CustomerCouponStatistics customerCouponStatistics,
                                               VisitHistories visitHistories, int unusedRewards) {
        return new CafeCustomerFindResultDto(
                customer.getId(),
                customer.getNickname(),
                customerCouponStatistics.getStampCount(),
                unusedRewards,
                visitHistories.getVisitCount(),
                visitHistories.getFirstVisitDate(),
                customer.isRegistered(),
                customerCouponStatistics.getMaxStampCount(),
                visitHistories.getRecentVisitDate()
        );
    }
}
