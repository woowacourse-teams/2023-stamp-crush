package com.stampcrush.backend.entity.cafe;

import com.stampcrush.backend.entity.baseentity.BaseDate;
import com.stampcrush.backend.entity.user.Owner;
import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

@Getter
@Entity
public class Cafe extends BaseDate {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String name;

    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime openTime;

    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime closeTime;

    private String telephoneNumber;

    private String cafeImageUrl;

    @Lob
    private String introduction;

    private String roadAddress;

    private String detailAddress;

    private String businessRegistrationNumber;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "owner_id")
    private Owner owner;

    @OneToMany(mappedBy = "cafe")
    private List<CafePolicy> policies = new ArrayList<>();

    public Cafe(String name, String roadAddress, String detailAddress, String businessRegistrationNumber, Owner owner) {
        this(name, null, null, null, null, null, roadAddress, detailAddress, businessRegistrationNumber, owner);
    }

    public Cafe(String name, LocalTime openTime, LocalTime closeTime, String telephoneNumber, String cafeImageUrl, String introduction, String roadAddress, String detailAddress, String businessRegistrationNumber, Owner owner) {
        this.name = name;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.telephoneNumber = telephoneNumber;
        this.cafeImageUrl = cafeImageUrl;
        this.introduction = introduction;
        this.roadAddress = roadAddress;
        this.detailAddress = detailAddress;
        this.businessRegistrationNumber = businessRegistrationNumber;
        this.owner = owner;
    }

    protected Cafe() {
    }

    public void updateCafeAdditionalInformation(String introduction, LocalTime openTime, LocalTime closeTime, String telephoneNumber, String cafeImageUrl) {
        this.introduction = introduction;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.telephoneNumber = telephoneNumber;
        this.cafeImageUrl = cafeImageUrl;
    }
}
