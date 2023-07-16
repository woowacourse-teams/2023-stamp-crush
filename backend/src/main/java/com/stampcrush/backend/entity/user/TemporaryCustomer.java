package com.stampcrush.backend.entity.user;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@DiscriminatorValue("temporary")
@Entity
public class TemporaryCustomer extends Customer {

    public TemporaryCustomer(String nickname, String phoneNumber) {
        super(nickname, phoneNumber);
    }

    protected TemporaryCustomer() {
    }

    @Override
    public boolean isRegistered() {
        return false;
    }
}
