package com.stampcrush.backend.entity.coupon;

import com.stampcrush.backend.entity.baseentity.BaseDate;
import com.stampcrush.backend.entity.cafe.Cafe;
import com.stampcrush.backend.entity.user.Customer;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static jakarta.persistence.FetchType.EAGER;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

@Getter
@Entity
public class Coupon extends BaseDate {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private LocalDate expiredDate;

    @Enumerated(EnumType.STRING)
    private CouponStatus status = CouponStatus.USING;

    private Boolean deleted = Boolean.TRUE;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "cafe_id")
    private Cafe cafe;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "coupon_design_id")
    private CouponDesign couponDesign;

    @OneToMany(mappedBy = "coupon", cascade = CascadeType.ALL, fetch = EAGER)
    private List<Stamp> stamps = new ArrayList<>();

    public Coupon(LocalDate expiredDate, Customer customer, Cafe cafe, CouponDesign couponDesign) {
        this.expiredDate = expiredDate;
        this.customer = customer;
        this.cafe = cafe;
        this.couponDesign = couponDesign;
    }

    protected Coupon() {
    }

    public void reward() {
        this.status = CouponStatus.REWARDED;
    }

    public boolean isUsing() {
        return this.status == CouponStatus.USING;
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
                .collect(Collectors.toSet())
                .size();
    }

    public LocalDateTime compareVisitTime(LocalDateTime visitTime) {
        if (this.getCreatedAt().isBefore(visitTime)) {
            return this.getCreatedAt();
        }
        return visitTime;
    }
}
