package com.stampcrush.backend.entity.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.IDENTITY;
import static jakarta.persistence.InheritanceType.SINGLE_TABLE;
import static lombok.AccessLevel.PROTECTED;

@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
@Getter
@Entity
@DiscriminatorColumn(name = "dtype")
@Inheritance(strategy = SINGLE_TABLE)
public abstract class Customer {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "customer_id")
    private Long id;
    private String nickname;
    private String phoneNumber;

    public Customer(String nickname, String phoneNumber) {
        this(null, nickname, phoneNumber);
    }

    public void registerPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public abstract boolean isRegistered();
}
