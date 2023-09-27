package com.stampcrush.backend.entity.coupon;

import com.stampcrush.backend.entity.baseentity.BaseDate;
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

@Getter
@NoArgsConstructor(access = PROTECTED)
@SQLDelete(sql = "UPDATE coupon_stamp_coordinate SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
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

   private Boolean deleted = Boolean.FALSE;


    public CouponStampCoordinate(Integer stampOrder, Integer xCoordinate, Integer yCoordinate, CouponDesign couponDesign) {
        this.stampOrder = stampOrder;
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        this.couponDesign = couponDesign;
    }
}
