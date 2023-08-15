package com.stampcrush.backend.entity.coupon;

import com.stampcrush.backend.entity.baseentity.BaseDate;
import com.stampcrush.backend.entity.cafe.CafePolicy;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
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

    public boolean isSameMaxStampCount(int stampCount) {
        return stampCount == maxStampCount;
    }

    public boolean isPrevious(CafePolicy currentCafePolicy) {
        boolean isSameMaxStampCount = this.maxStampCount.equals(currentCafePolicy.getMaxStampCount());
        boolean isSameReward = this.rewardName.equals(currentCafePolicy.getReward());
        boolean isSameExpirePeriod = this.expiredPeriod.equals(currentCafePolicy.getExpirePeriod());

        return !(isSameMaxStampCount && isSameReward && isSameExpirePeriod);
    }
}
