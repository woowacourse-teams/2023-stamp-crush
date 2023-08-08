package com.stampcrush.backend.repository.user;

import com.stampcrush.backend.common.KorNamingConverter;
import com.stampcrush.backend.entity.user.RegisterCustomer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@KorNamingConverter
@DataJpaTest
class RegisterCustomerRepositoryTest {

    @Autowired
    private RegisterCustomerRepository registerCustomerRepository;

    @Test
    void 주어진_LoginId에_해당하는_고객을_조회한다() {
        // given
        String loginId = "jenaId";
        RegisterCustomer registerCustomer = new RegisterCustomer("jena", "01012345678", loginId, "jenapw");
        RegisterCustomer savedCustomer = registerCustomerRepository.save(registerCustomer);

        // when
        Optional<RegisterCustomer> findCustomer = registerCustomerRepository.findByLoginId(loginId);

        // then
        assertThat(findCustomer.get()).isEqualTo(savedCustomer);
    }

    @Test
    void 존재하지_않는_LoginId로_고객을_조회하면_빈_Optional을_반환한다() {
        // given
        String notExistLoginId = "notExist";

        // when
        Optional<RegisterCustomer> findCustomer = registerCustomerRepository.findByLoginId(notExistLoginId);

        // then
        assertThat(findCustomer).isEmpty();
    }
}
