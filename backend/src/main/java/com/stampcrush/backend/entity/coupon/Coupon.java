package com.stampcrush.backend.entity.coupon;

import com.stampcrush.backend.entity.baseentity.BaseDate;
import com.stampcrush.backend.entity.cafe.Cafe;
import com.stampcrush.backend.entity.cafe.CafeCouponDesign;
import com.stampcrush.backend.entity.cafe.CafePolicy;
import com.stampcrush.backend.entity.user.Customer;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "cafe_coupon_design_id")
    private CafeCouponDesign cafeCouponDesign;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "cafe_policy_id")
    private CafePolicy cafePolicy;

    @OneToMany(mappedBy = "coupon", fetch = LAZY, cascade = CascadeType.ALL)
    private List<Stamp> stamps = new ArrayList<>();

    public Coupon(LocalDateTime createdAt, LocalDateTime updatedAt,
                  LocalDate expiredDate, Customer customer,
                  Cafe cafe, CafeCouponDesign cafeCouponDesign, CafePolicy cafePolicy) {
        super(createdAt, updatedAt);
        this.expiredDate = expiredDate;
        this.customer = customer;
        this.cafe = cafe;
        this.cafeCouponDesign = cafeCouponDesign;
        this.cafePolicy = cafePolicy;
    }

    public Coupon(LocalDate expiredDate, Customer customer, Cafe cafe, CafeCouponDesign cafeCouponDesign, CafePolicy cafePolicy) {
        this(null, null, expiredDate, customer, cafe, cafeCouponDesign, cafePolicy);
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

    public LocalDateTime calculateExpireDate() {
        return this.getCreatedAt().plusMonths(this.cafePolicy.getExpirePeriod());
    }

    public boolean isNotAccessible(Customer customer, Cafe cafe) {
        return !this.customer.equals(customer) || !this.cafe.equals(cafe);
    }

    public void accumulate(int earningStampCount) {
        for (int i = 0; i < earningStampCount; i++) {
            Stamp stamp = new Stamp();
            stamp.registerCoupon(this);
        }
        if (cafePolicy.isSameMaxStampCount(stamps.size())) {
            status = CouponStatus.REWARDED;
        }
    }

    public void accumulateMaxStamp() {
        for (int i = 0; i < cafePolicy.getMaxStampCount(); i++) {
            Stamp stamp = new Stamp();
            stamp.registerCoupon(this);
        }
        status = CouponStatus.REWARDED;
    }

    public int getCouponMaxStampCount() {
        return cafePolicy.getMaxStampCount();
    }

    public int calculateMaxStampCountWhenAccumulating() {
        if (status == CouponStatus.ACCUMULATING) {
            return cafePolicy.getMaxStampCount();
        }
        return 0;
    }

    public boolean isLessThanMaxStampAfterAccumulateStamp(int earningStampCount) {
        return this.stamps.size() + earningStampCount < cafePolicy.getMaxStampCount();
    }

    public boolean isSameMaxStampAfterAccumulateStamp(int earningStampCount) {
        return this.stamps.size() + earningStampCount == cafePolicy.getMaxStampCount();
    }

    public int calculateRestStampCountForReward() {
        return cafePolicy.getMaxStampCount() - this.stamps.size();
    }

    public String getRewardName() {
        return cafePolicy.getReward();
    }

    public boolean isPrevious() {
        return cafePolicy.isPrevious();
    }
}
