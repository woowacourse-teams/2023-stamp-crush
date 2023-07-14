package com.stampcrush.backend.entity.user;

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

