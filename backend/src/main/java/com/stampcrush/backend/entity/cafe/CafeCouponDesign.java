package com.stampcrush.backend.entity.cafe;

import com.stampcrush.backend.entity.baseentity.BaseDate;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
@SQLDelete(sql = "UPDATE cafe_coupon_design SET deleted = true WHERE id = ?")
@Entity
public class CafeCouponDesign extends BaseDate {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String frontImageUrl;

    private String backImageUrl;

    private String stampImageUrl;

    private Boolean deleted = Boolean.FALSE;

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

    public void addCouponStampCoordinate(CafeStampCoordinate cafeStampCoordinate) {
        cafeStampCoordinates.add(cafeStampCoordinate);
    }
}
