package com.stampcrush.backend.entity.coupon;

import com.stampcrush.backend.entity.baseentity.BaseDate;
import com.stampcrush.backend.entity.cafe.Cafe;
import com.stampcrush.backend.entity.user.Customer;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Entity
public class Coupon extends BaseDate {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private LocalDate expiredDate;

    @Enumerated(EnumType.STRING)
    private CouponStatus status = CouponStatus.ACCUMULATING;

    private Boolean deleted = Boolean.FALSE;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "cafe_id")
    private Cafe cafe;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "coupon_design_id")
    private CouponDesign couponDesign;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "coupon_policy_id")
    private CouponPolicy couponPolicy;

    @OneToMany(mappedBy = "coupon", cascade = CascadeType.ALL, fetch = LAZY)
    private List<Stamp> stamps = new ArrayList<>();

    public Coupon(LocalDate expiredDate, Customer customer, Cafe cafe, CouponDesign couponDesign, CouponPolicy couponPolicy) {
        this.expiredDate = expiredDate;
        this.customer = customer;
        this.cafe = cafe;
        this.couponDesign = couponDesign;
        this.couponPolicy = couponPolicy;
    }

    public void reward() {
        this.status = CouponStatus.REWARDED;
    }

    public void expire() {
        this.status = CouponStatus.EXPIRED;
    }

    public boolean isAccumulating() {
        return this.status == CouponStatus.ACCUMULATING;
    }

    public boolean isRewarded() {
        return this.status == CouponStatus.REWARDED;
    }

    public int getStampCount() {
        return stamps.size();
    }

    public int calculateVisitCount() {
        return stamps.stream()
                .map(BaseDate::getCreatedAt)
                .map(date -> LocalDateTime.of(date.getYear(), date.getMonth(), date.getDayOfMonth(), date.getHour(), date.getMinute()))
                .collect(Collectors.toSet())
                .size();
    }

    public LocalDateTime compareCreatedAtAndReturnEarlier(LocalDateTime visitTime) {
        if (this.getCreatedAt().isBefore(visitTime)) {
            return this.getCreatedAt();
        }
        return visitTime;
    }

    public LocalDateTime calculateExpireDate() {
        return this.getCreatedAt().plusMonths(this.couponPolicy.getExpiredPeriod());
    }

    public boolean isNotAccessible(Customer customer, Cafe cafe) {
        return !this.customer.equals(customer) || !this.cafe.equals(cafe);
    }

    public void accumulate(int earningStampCount) {
        for (int i = 0; i < earningStampCount; i++) {
            Stamp stamp = new Stamp();
            stamp.registerCoupon(this);
        }
        if (couponPolicy.isSameMaxStampCount(stamps.size())) {
            status = CouponStatus.REWARDED;
        }
    }

    public void accumulateMaxStamp() {
        for (int i = 0; i < couponPolicy.getMaxStampCount(); i++) {
            Stamp stamp = new Stamp();
            stamp.registerCoupon(this);
        }
        status = CouponStatus.REWARDED;
    }

    public int getCouponMaxStampCount() {
        return couponPolicy.getMaxStampCount();
    }

    public int calculateMaxStampCountWhenAccumulating() {
        if (status == CouponStatus.ACCUMULATING) {
            return couponPolicy.getMaxStampCount();
        }
        return 0;
    }

    public boolean isLessThanMaxStampAfterAccumulateStamp(int earningStampCount) {
        return this.stamps.size() + earningStampCount < couponPolicy.getMaxStampCount();
    }

    public void accumulateEarningStamp(int earningStampCount) {
        for (int i = 0; i < earningStampCount; i++) {
            Stamp stamp = new Stamp();
            stamp.registerCoupon(this);
        }
    }

    public boolean isSameMaxStampAfterAccumulateStamp(int earningStampCount) {
        return this.stamps.size() + earningStampCount == couponPolicy.getMaxStampCount();
    }

    public String getRewardName() {
        return couponPolicy.getRewardName();
    }
}
