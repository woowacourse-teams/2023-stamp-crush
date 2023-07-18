package com.stampcrush.backend.entity.coupon;

import com.stampcrush.backend.entity.baseentity.BaseDate;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;

import static jakarta.persistence.GenerationType.IDENTITY;

@Getter
@Entity
public class CouponPolicy extends BaseDate {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private Integer maxStampCount;

    private String rewardName;

    private Integer expiredPeriod;

    public CouponPolicy(Integer maxStampCount, String rewardName, Integer expiredPeriod) {
        this.maxStampCount = maxStampCount;
        this.rewardName = rewardName;
        this.expiredPeriod = expiredPeriod;
    }

    protected CouponPolicy() {
    }
}
