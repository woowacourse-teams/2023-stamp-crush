package com.stampcrush.backend.fixture;

import com.stampcrush.backend.entity.user.RegisterCustomer;
import com.stampcrush.backend.entity.user.TemporaryCustomer;

public final class CustomerFixture {

    public static final TemporaryCustomer TEMPORARY_CUSTOMER_1 = TemporaryCustomer.from("깃짱 번호");
    public static final TemporaryCustomer TEMPORARY_CUSTOMER_2 = TemporaryCustomer.from("깃짱 번호");
    public static final TemporaryCustomer TEMPORARY_CUSTOMER_3 = TemporaryCustomer.from("깃짱 번호");
    public static final RegisterCustomer REGISTER_CUSTOMER_1 = new RegisterCustomer("깃짱 닉네임", "깃짱 번호", "깃짱 아이디", "깃짱 비번");
    public static final RegisterCustomer REGISTER_CUSTOMER_2 = new RegisterCustomer("깃짱 닉네임", "깃짱 번호", "깃짱 아이디", "깃짱 비번");
    public static final RegisterCustomer REGISTER_CUSTOMER_GITCHAN = new RegisterCustomer("깃짱", "01012345678", "gitchan", "password");
    public static final RegisterCustomer REGISTER_CUSTOMER_GITCHAN_SAVED = new RegisterCustomer(1L, "깃짱", "01012345678", "gitchan", "password");
    public static final RegisterCustomer REGISTER_CUSTOMER_JENA = new RegisterCustomer("jena", "01012345678", "jena", "1234");

    private CustomerFixture() {
    }
}
