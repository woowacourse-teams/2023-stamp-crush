package com.stampcrush.backend.entity.coupon;

import com.stampcrush.backend.entity.baseentity.BaseDate;
import com.stampcrush.backend.entity.cafe.Cafe;
import com.stampcrush.backend.entity.cafe.CafePolicy;
import com.stampcrush.backend.entity.user.Customer;
import com.stampcrush.backend.exception.CafePolicyNotFoundException;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

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
@SQLDelete(sql = "UPDATE coupon SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
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

    @OneToOne(fetch = LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "coupon_design_id")
    private CouponDesign couponDesign;

    @OneToOne(fetch = LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "coupon_policy_id")
    private CouponPolicy couponPolicy;

    @OneToMany(mappedBy = "coupon", fetch = LAZY, cascade = CascadeType.ALL)
    private List<Stamp> stamps = new ArrayList<>();

    public Coupon(LocalDateTime createdAt, LocalDateTime updatedAt,
                  LocalDate expiredDate, Customer customer,
                  Cafe cafe, CouponDesign couponDesign, CouponPolicy couponPolicy) {
        super(createdAt, updatedAt);
        this.expiredDate = expiredDate;
        this.customer = customer;
        this.cafe = cafe;
        this.couponDesign = couponDesign;
        this.couponPolicy = couponPolicy;
    }

    public Coupon(LocalDate expiredDate, Customer customer, Cafe cafe, CouponDesign couponDesign, CouponPolicy couponPolicy) {
        this(null, null, expiredDate, customer, cafe, couponDesign, couponPolicy);
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

    public boolean isSameMaxStampAfterAccumulateStamp(int earningStampCount) {
        return this.stamps.size() + earningStampCount == couponPolicy.getMaxStampCount();
    }

    public int calculateRestStampCountForReward() {
        return couponPolicy.getMaxStampCount() - this.stamps.size();
    }

    public String getRewardName() {
        return couponPolicy.getRewardName();
    }

    public boolean isPrevious() {
        CafePolicy currentCafePolicy = findCurrentCafePolicy();
        return couponPolicy.isPrevious(currentCafePolicy);
    }

    private CafePolicy findCurrentCafePolicy() {
        List<CafePolicy> policies = cafe.getPolicies();

        if (policies.isEmpty()) {
            throw new CafePolicyNotFoundException("해당하는 카페의 정책이 존재하지 않습니다.");
        }

        return policies.get(0);
    }
}
