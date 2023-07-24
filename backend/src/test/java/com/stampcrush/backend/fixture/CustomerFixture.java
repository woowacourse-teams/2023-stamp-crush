package com.stampcrush.backend.fixture;

import com.stampcrush.backend.entity.user.RegisterCustomer;
import com.stampcrush.backend.entity.user.TemporaryCustomer;

public class CustomerFixture {

    public static final TemporaryCustomer TEMPORARY_CUSTOMER_1 = new TemporaryCustomer("깃짱 번호");
    public static final TemporaryCustomer TEMPORARY_CUSTOMER_2 = new TemporaryCustomer( "깃짱 번호");
    public static final TemporaryCustomer TEMPORARY_CUSTOMER_3 = new TemporaryCustomer("깃짱 번호");
    public static final RegisterCustomer REGISTER_CUSTOMER_1 = new RegisterCustomer("깃짱 닉네임", "깃짱 번호", "깃짱 아이디", "깃짱 비번");
    public static final RegisterCustomer REGISTER_CUSTOMER_2 = new RegisterCustomer("깃짱 닉네임", "깃짱 번호", "깃짱 아이디", "깃짱 비번");

    private CustomerFixture() {
    }
}
