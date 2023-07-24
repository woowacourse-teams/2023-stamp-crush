package com.stampcrush.backend.entity.user;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@NoArgsConstructor(access = PROTECTED)
@DiscriminatorValue("temporary")
@Entity
public class TemporaryCustomer extends Customer {

    private static final int NICKNAME_LENGTH = 4;

    public TemporaryCustomer(String phoneNumber) {
        super(phoneNumber.substring(phoneNumber.length() - NICKNAME_LENGTH), phoneNumber);
    }

    @Override
    public boolean isRegistered() {
        return false;
    }
}
