package com.stampcrush.backend.entity.user;

public enum CustomerType {

    TEMPORARY("register"),
    REGISTERED("temporary");

    private final String customerType;

    CustomerType(String customerType) {
        this.customerType = customerType;
    }

    public String getCustomerType() {
        return customerType;
    }
}
