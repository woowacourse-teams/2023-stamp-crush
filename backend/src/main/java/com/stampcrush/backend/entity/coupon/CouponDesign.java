package com.stampcrush.backend.entity.coupon;

import com.stampcrush.backend.entity.baseentity.BaseDate;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Entity
public class CouponDesign extends BaseDate {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String frontImageUrl;

    private String backImageUrl;

    private String stampImageUrl;

    @OneToMany(mappedBy = "couponDesign", cascade = ALL)
    private List<CouponStampCoordinate> couponStampCoordinates = new ArrayList<>();

    public CouponDesign(String frontImageUrl, String backImageUrl, String stampImageUrl) {
        this.frontImageUrl = frontImageUrl;
        this.backImageUrl = backImageUrl;
        this.stampImageUrl = stampImageUrl;
    }

    public void addCouponStampCoordinate(CouponStampCoordinate couponStampCoordinate) {
        couponStampCoordinates.add(couponStampCoordinate);
    }
}
