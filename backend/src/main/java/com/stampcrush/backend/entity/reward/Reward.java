package com.stampcrush.backend.entity.reward;

import com.stampcrush.backend.entity.baseentity.BaseDate;
import com.stampcrush.backend.entity.cafe.Cafe;
import com.stampcrush.backend.entity.user.Customer;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;

import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

@Getter
@Entity
public class Reward extends BaseDate {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String name;

    private Boolean used = false;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "cafe_id")
    private Cafe cafe;

    public Reward(
            LocalDateTime createdAt,
            LocalDateTime updatedAt,
            Long id,
            String name,
            Boolean used,
            Customer customer,
            Cafe cafe
    ) {
        super(createdAt, updatedAt);
        this.id = id;
        this.name = name;
        this.used = used;
        this.customer = customer;
        this.cafe = cafe;
    }

    public Reward(Long id, String name, Boolean used, Customer customer, Cafe cafe) {
        this(null, null, id, name, used, customer, cafe);
    }

    public Reward(String name, Customer customer, Cafe cafe) {
        this(null, name, false, customer, cafe);
    }

    protected Reward() {
    }

    public void useReward(Customer customer, Cafe cafe) {
        if (used) {
            throw new IllegalArgumentException("이미 사용된 리워드 입니다.");
        }
        if (isNotPublisher(cafe)) {
            throw new IllegalArgumentException("해당 카페에서 발행된 리워드가 아닙니다.");
        }
        if (isNotOwner(customer)) {
            throw new IllegalArgumentException("해당 리워드의 소유자가 아닙니다.");
        }
        used = true;
    }

    private boolean isNotPublisher(Cafe cafe) {
        return !cafe.equals(this.cafe);
    }

    private boolean isNotOwner(Customer customer) {
        return !customer.equals(this.customer);
    }
}
