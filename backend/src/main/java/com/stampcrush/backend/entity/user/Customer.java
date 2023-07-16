package com.stampcrush.backend.entity.user;

import jakarta.persistence.*;
import lombok.Getter;

import static jakarta.persistence.GenerationType.IDENTITY;
import static jakarta.persistence.InheritanceType.JOINED;

@Getter
@Entity
@DiscriminatorColumn(name = "dtype")
@Inheritance(strategy = JOINED)
public abstract class Customer {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "customer_id")
    private Long id;

    private String nickname;

    private String phoneNumber;

    public Customer(String nickname, String phoneNumber) {
        this.nickname = nickname;
        this.phoneNumber = phoneNumber;
    }

    protected Customer() {
    }

    public abstract boolean isRegistered();
}
