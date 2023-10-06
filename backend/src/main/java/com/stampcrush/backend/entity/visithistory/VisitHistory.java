package com.stampcrush.backend.entity.visithistory;

import com.stampcrush.backend.entity.baseentity.BaseDate;
import com.stampcrush.backend.entity.cafe.Cafe;
import com.stampcrush.backend.entity.user.Customer;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@NoArgsConstructor(access = PROTECTED)
@SQLDelete(sql = "UPDATE visit_history SET deleted = true WHERE customer_id = ?")
@Where(clause = "deleted = false")
@Getter
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

    private Boolean deleted = false;

    public VisitHistory(LocalDateTime createdAt, LocalDateTime updatedAt, Cafe cafe, Customer customer, int stampCount) {
        super(createdAt, updatedAt);
        this.cafe = cafe;
        this.customer = customer;
        this.stampCount = stampCount;
    }

    public VisitHistory(Cafe cafe, Customer customer, int stampCount) {
        this(null, null, cafe, customer, stampCount);
    }

    public String getCafeName() {
        return cafe.getName();
    }
}
