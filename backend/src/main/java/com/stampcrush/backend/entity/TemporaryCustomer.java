package com.stampcrush.backend.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@DiscriminatorValue("temporary")
@Entity
public class TemporaryCustomer extends Customer {
}
