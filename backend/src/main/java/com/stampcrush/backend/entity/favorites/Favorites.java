package com.stampcrush.backend.entity.favorites;

import com.stampcrush.backend.entity.baseentity.BaseDate;
import com.stampcrush.backend.entity.cafe.Cafe;
import com.stampcrush.backend.entity.user.Customer;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Entity
public class Favorites extends BaseDate {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "cafe_id")
    private Cafe cafe;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    private Boolean isFavorites = false;

    public Favorites(Cafe cafe, Customer customer) {
        this.cafe = cafe;
        this.customer = customer;
    }

    public Favorites(Cafe cafe, Customer customer, Boolean isFavorites) {
        this.cafe = cafe;
        this.customer = customer;
        this.isFavorites = isFavorites;
    }

    public void changeFavorites(boolean isFavorites) {
        this.isFavorites = isFavorites;
    }
}
