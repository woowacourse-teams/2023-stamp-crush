package com.stampcrush.backend.entity;

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
}
