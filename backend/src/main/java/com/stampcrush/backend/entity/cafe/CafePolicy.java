package com.stampcrush.backend.entity.cafe;

import com.stampcrush.backend.entity.baseentity.BaseDate;
import com.stampcrush.backend.entity.coupon.CouponPolicy;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@NoArgsConstructor(access = PROTECTED)
@Getter
@SQLDelete(sql = "UPDATE cafe_policy SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
@Entity
public class CafePolicy extends BaseDate {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private Integer maxStampCount;

    private String reward;

    private Integer expirePeriod;

    private Boolean deleted = Boolean.FALSE;
    private Boolean isActivate = Boolean.TRUE;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "cafe_id")
    private Cafe cafe;

    public CafePolicy(Integer maxStampCount, String reward, Integer expirePeriod, Cafe cafe) {
        this.maxStampCount = maxStampCount;
        this.reward = reward;
        this.expirePeriod = expirePeriod;
        this.cafe = cafe;
    }

    public static CafePolicy createDefaultCafePolicy(Cafe cafe) {
        return new CafePolicy(10, "아메리카노 1잔", 6, cafe);
    }

    public void delete() {
        this.deleted = true;
    }

    public void disable() {
        this.isActivate = Boolean.FALSE;
    }

    public CouponPolicy copy() {
        return new CouponPolicy(maxStampCount, reward, expirePeriod);
    }

    public int calculateRewardCouponCount(int earningStampCount) {
        return earningStampCount / maxStampCount;
    }

    public boolean isSameMaxStampCount(int stampCount) {
        return stampCount == maxStampCount;
    }
}
