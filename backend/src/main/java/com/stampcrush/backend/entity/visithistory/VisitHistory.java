package com.stampcrush.backend.entity.visithistory;

import com.stampcrush.backend.entity.baseentity.BaseDate;
import com.stampcrush.backend.entity.cafe.Cafe;
import com.stampcrush.backend.entity.user.Customer;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
@Entity
public class VisitHistory extends BaseDate {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "cafe_id")
    private Cafe cafe;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    private int stampCount;

    public VisitHistory(Cafe cafe, Customer customer, int stampCount) {
        this(null, cafe, customer, stampCount);
    }

    public VisitHistory(LocalDateTime createdAt, LocalDateTime updatedAt, Cafe cafe, Customer customer, int stampCount) {
        super(createdAt, updatedAt);
        this.cafe = cafe;
        this.customer = customer;
        this.stampCount = stampCount;
    }
}
