package com.stampcrush.backend.fixture;

import com.stampcrush.backend.entity.user.Customer;

public final class CustomerFixture {

    public static final Customer TEMPORARY_CUSTOMER_1 = Customer.temporaryCustomerBuilder()
            .phoneNumber("깃짱 번호")
            .build();
    public static final Customer TEMPORARY_CUSTOMER_2 = Customer.temporaryCustomerBuilder()
            .phoneNumber("깃짱 번호")
            .build();
    public static final Customer TEMPORARY_CUSTOMER_3 = Customer.temporaryCustomerBuilder()
            .phoneNumber("깃짱 번호")
            .build();
    public static final Customer REGISTER_CUSTOMER_1 = Customer.registeredCustomerBuilder()
            .nickname("깃짱 닉네임")
            .phoneNumber("깃짱 번호")
            .build();
    public static final Customer REGISTER_CUSTOMER_2 = Customer.registeredCustomerBuilder()
            .nickname("깃짱 닉네임")
            .phoneNumber("깃짱 번호")
            .build();
    public static final Customer REGISTER_CUSTOMER_GITCHAN = Customer.registeredCustomerBuilder()
            .nickname("깃짱")
            .phoneNumber("01012345678")
            .loginId("loginId")
            .encryptedPassword("pw")
            .build();
    public static final Customer REGISTER_CUSTOMER_GITCHAN_SAVED = Customer.registeredCustomerBuilder()
            .id(1L)
            .nickname("깃짱")
            .phoneNumber("01012345678")
            .loginId("loginId")
            .encryptedPassword("pw")
            .build();
    public static final Customer REGISTER_CUSTOMER_JENA = Customer.registeredCustomerBuilder()
            .nickname("jena")
            .phoneNumber("01012345678")
            .loginId("jenaId")
            .encryptedPassword("jenaPw")
            .build();
    public static final Customer REGISTER_CUSTOMER_YOUNGHO = Customer.registeredCustomerBuilder()
            .nickname("name")
            .phoneNumber("phone")
            .loginId("0h0Id")
            .encryptedPassword("0h0Pw")
            .build();

    private CustomerFixture() {
    }
}
