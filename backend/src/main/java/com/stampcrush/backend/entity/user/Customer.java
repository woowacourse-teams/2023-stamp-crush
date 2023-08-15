package com.stampcrush.backend.entity.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.IDENTITY;
import static jakarta.persistence.InheritanceType.JOINED;
import static lombok.AccessLevel.PROTECTED;

@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
@Getter
@Entity
@DiscriminatorColumn(name = "dtype")
@Inheritance(strategy = JOINED)
public abstract class Customer {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "customer_id")
    private Long id;

    @Column(name = "name")
    private String nickname;

    private String phoneNumber;

    protected Customer(String nickname, String phoneNumber) {
        this(null, nickname, phoneNumber);
    }

    public abstract boolean isRegistered();
}
