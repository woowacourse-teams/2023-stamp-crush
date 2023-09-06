package com.stampcrush.backend.api.visitor.profile.response;

import com.stampcrush.backend.application.visitor.profile.dto.VisitorProfileFindByPhoneNumberResulteDto;
import com.stampcrush.backend.entity.user.Customer;
import com.stampcrush.backend.fixture.CustomerFixture;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class VisitorProfilesFindByPhoneNumberResponseTest {

    @Test
    void 응답_생성_테스트() {
        Customer customer = CustomerFixture.REGISTER_CUSTOMER_GITCHAN;
        VisitorProfileFindByPhoneNumberResulteDto dto = VisitorProfileFindByPhoneNumberResulteDto.from(customer);
        VisitorProfilesFindByPhoneNumberResponse response = VisitorProfilesFindByPhoneNumberResponse.from(dto);

        assertThat(response.getCustomers()).isNotEmpty();
    }
}
