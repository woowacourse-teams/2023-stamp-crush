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
    private String nickname;

    @Column(unique = true)
    private String phoneNumber;

    public Customer(String nickname, String phoneNumber) {
        this(null, nickname, phoneNumber);
    }

    public void registerPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public abstract boolean isRegistered();
}
