package com.stampcrush.backend.entity.cafe;

import com.stampcrush.backend.entity.baseentity.BaseDate;
import jakarta.persistence.*;
import lombok.Getter;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

@Getter
@Entity
public class CafeStampCoordinate extends BaseDate {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private Integer stampOrder;

    private Integer xCoordinate;

    private Integer yCoordinate;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "cafe_coupon_design_id")
    private CafeCouponDesign cafeCouponDesign;

    public CafeStampCoordinate(Integer stampOrder, Integer xCoordinate, Integer yCoordinate, CafeCouponDesign cafeCouponDesign) {
        this.stampOrder = stampOrder;
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        this.cafeCouponDesign = cafeCouponDesign;
    }

    protected CafeStampCoordinate() {
    }
}
