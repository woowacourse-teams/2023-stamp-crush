package com.stampcrush.backend.entity.user;

import com.stampcrush.backend.entity.user.Customer;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@DiscriminatorValue("temporary")
@Entity
public class TemporaryCustomer extends Customer {
}
