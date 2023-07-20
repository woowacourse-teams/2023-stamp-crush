package com.stampcrush.backend.entity.coupon;

import com.stampcrush.backend.entity.baseentity.BaseDate;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Entity
public class CouponStampCoordinate extends BaseDate {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private Integer stampOrder;

    private Integer xCoordinate;

    private Integer yCoordinate;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "coupon_design_id")
    private CouponDesign couponDesign;

    public CouponStampCoordinate(Integer stampOrder, Integer xCoordinate, Integer yCoordinate, CouponDesign couponDesign) {
        this.stampOrder = stampOrder;
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        this.couponDesign = couponDesign;
    }
}
