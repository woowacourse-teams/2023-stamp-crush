package com.stampcrush.backend.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;

@Getter
@DiscriminatorValue("register")
@Entity
public class RegisterCustomer extends Customer {

    private String loginId;
    private String encryptedPassword;
}

