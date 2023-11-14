package com.stampcrush.backend.entity.cafe;

import com.stampcrush.backend.entity.baseentity.BaseDate;
import com.stampcrush.backend.entity.coupon.CouponDesign;
import com.stampcrush.backend.entity.coupon.CouponStampCoordinate;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
@SQLDelete(sql = "UPDATE cafe_coupon_design SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
@Entity
public class CafeCouponDesign extends BaseDate {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String frontImageUrl;

    private String backImageUrl;

    private String stampImageUrl;

    private Boolean deleted = Boolean.FALSE;
    private Boolean isActivate = Boolean.TRUE;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "cafe_id")
    private Cafe cafe;

    @OneToMany(mappedBy = "cafeCouponDesign")
    private List<CafeStampCoordinate> cafeStampCoordinates = new ArrayList<>();

    public CafeCouponDesign(String frontImageUrl, String backImageUrl, String stampImageUrl, Cafe cafe) {
        this.frontImageUrl = frontImageUrl;
        this.backImageUrl = backImageUrl;
        this.stampImageUrl = stampImageUrl;
        this.cafe = cafe;
    }

    public void delete() {
        this.deleted = true;
    }

    public void disable() {
        this.isActivate = Boolean.FALSE;
    }

    public CouponDesign copy() {
        CouponDesign couponDesign = new CouponDesign(frontImageUrl, backImageUrl, stampImageUrl);
        for (CafeStampCoordinate cafeStampCoordinate : cafeStampCoordinates) {
            CouponStampCoordinate couponStampCoordinate = cafeStampCoordinate.copy(couponDesign);
            couponDesign.addCouponStampCoordinate(couponStampCoordinate);
        }
        return couponDesign;
    }
}
