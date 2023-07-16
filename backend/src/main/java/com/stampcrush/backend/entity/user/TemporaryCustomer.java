package com.stampcrush.backend.entity.user;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@DiscriminatorValue("temporary")
@Entity
public class TemporaryCustomer extends Customer {

    @Override
    public boolean isRegistered() {
        return false;
    }
}
