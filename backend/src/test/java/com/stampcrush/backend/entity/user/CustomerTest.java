package com.stampcrush.backend.entity.user;

import com.stampcrush.backend.common.KorNamingConverter;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@KorNamingConverter
class CustomerTest {

    @Test
    void 정식회원인지_확인한다() {
        Customer registerCustomer = Customer.registeredCustomerBuilder().build();

        assertThat(registerCustomer.isRegistered()).isTrue();
    }

    @Test
    void 임시회원인지_확인한다() {
        Customer temporaryCustomer = Customer.temporaryCustomerBuilder()
                .phoneNumber("01038626099")
                .build();

        assertThat(temporaryCustomer.isRegistered()).isFalse();
    }

}
