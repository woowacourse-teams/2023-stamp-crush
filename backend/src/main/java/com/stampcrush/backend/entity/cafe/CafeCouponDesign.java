package com.stampcrush.backend.entity.cafe;

import com.stampcrush.backend.entity.baseentity.BaseDate;
import jakarta.persistence.*;
import lombok.Getter;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

@Getter
@Entity
public class CafeCouponDesign extends BaseDate {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String frontImageUrl;

    private String backImageUrl;

    private String stampImageUrl;

    private Boolean deleted;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "cafe_id")
    private Cafe cafe;

    public CafeCouponDesign(String frontImageUrl, String backImageUrl, String stampImageUrl, Boolean deleted, Cafe cafe) {
        this.frontImageUrl = frontImageUrl;
        this.backImageUrl = backImageUrl;
        this.stampImageUrl = stampImageUrl;
        this.deleted = deleted;
        this.cafe = cafe;
    }

    protected CafeCouponDesign() {
    }
}
