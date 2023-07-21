package com.stampcrush.backend.application.coupon.dto;

import com.stampcrush.backend.application.coupon.CouponService;
import com.stampcrush.backend.entity.user.Customer;
import lombok.*;

import java.time.LocalDateTime;

@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
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

    public static CafeCustomerFindResultDto from(Customer customer, CouponService.CustomerInfo customerInfo, int maxStampCount) {
        return new CafeCustomerFindResultDto(
                customer.getId(),
                customer.getNickname(),
                customerInfo.stampCount(),
                customerInfo.rewardCount(),
                customerInfo.visitCount(),
                customerInfo.firstVisitDate(),
                customer.isRegistered(),
                maxStampCount
        );
    }
}
