package com.stampcrush.backend.entity.cafe;

import com.stampcrush.backend.entity.baseentity.BaseDate;
import jakarta.persistence.*;
import lombok.Getter;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

@Getter
@Entity
public class CafePolicy extends BaseDate {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private Integer maxStampCount;

    private String reward;

    private Integer expirePeriod;

    private Boolean deleted;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "cafe_id")
    private Cafe cafe;

    public CafePolicy(Integer maxStampCount, String reward, Integer expirePeriod, Boolean deleted, Cafe cafe) {
        this.maxStampCount = maxStampCount;
        this.reward = reward;
        this.expirePeriod = expirePeriod;
        this.deleted = deleted;
        this.cafe = cafe;
    }

    protected CafePolicy() {
    }

    public static CafePolicy createDefaultCafePolicy(Cafe cafe) {
        return new CafePolicy(10, "No", 6, false, cafe);
    }

    public void delete() {
        this.deleted = true;
    }
}
