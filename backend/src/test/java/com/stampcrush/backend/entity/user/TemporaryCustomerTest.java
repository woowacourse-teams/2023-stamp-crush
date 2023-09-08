package com.stampcrush.backend.entity.user;

import com.stampcrush.backend.common.KorNamingConverter;
import com.stampcrush.backend.exception.CustomerBadRequestException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@KorNamingConverter
class TemporaryCustomerTest {

    @Test
    void 전화번호_길이가_4자_이하일때_닉네임_생성_안되어_에러_발생() {
        // given
        String invalidPhoneNumber = "010";

        // when, then
        assertThatThrownBy(() -> Customer.temporaryCustomerBuilder()
                .phoneNumber(invalidPhoneNumber)
                .build()).isInstanceOf(CustomerBadRequestException.class);
    }
}
