package com.stampcrush.backend.entity.coupon;

import com.stampcrush.backend.entity.baseentity.BaseDate;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
@SQLDelete(sql = "UPDATE coupon_design SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
@Entity
public class CouponDesign extends BaseDate {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String frontImageUrl;

    private String backImageUrl;

    private String stampImageUrl;

    @OneToMany(mappedBy = "couponDesign", cascade = CascadeType.ALL)
    private List<CouponStampCoordinate> couponStampCoordinates = new ArrayList<>();

        private Boolean deleted = Boolean.FALSE;

    public CouponDesign(String frontImageUrl, String backImageUrl, String stampImageUrl) {
        this.frontImageUrl = frontImageUrl;
        this.backImageUrl = backImageUrl;
        this.stampImageUrl = stampImageUrl;
    }

    public void addCouponStampCoordinate(CouponStampCoordinate couponStampCoordinate) {
        couponStampCoordinates.add(couponStampCoordinate);
    }
}
