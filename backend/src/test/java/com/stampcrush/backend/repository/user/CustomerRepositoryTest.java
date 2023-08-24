package com.stampcrush.backend.repository.user;

import com.stampcrush.backend.common.KorNamingConverter;
import com.stampcrush.backend.entity.user.Customer;
import com.stampcrush.backend.entity.user.RegisterCustomer;
import com.stampcrush.backend.entity.user.TemporaryCustomer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;

import static com.stampcrush.backend.fixture.CustomerFixture.REGISTER_CUSTOMER_GITCHAN;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@KorNamingConverter
@DataJpaTest
class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    void 주어진_전화번호에_해당하는_고객을_조회한다() {
        // given
        String phoneNumber = "01012345678";
        Customer customer = TemporaryCustomer.from(phoneNumber);
        Customer savedCustomer = customerRepository.save(customer);

        // when
        List<Customer> customers = customerRepository.findByPhoneNumber(phoneNumber);

        // then
        assertThat(customers.get(0)).isEqualTo(savedCustomer);
    }

    @Test
    void 존재하지_않는_전화번호로_고객을_조회하면_빈_리스트를_반환한다() {
        // given
        String notExistPhoneNumber = "01012345678";

        // when
        List<Customer> customers = customerRepository.findByPhoneNumber(notExistPhoneNumber);

        // then
        assertThat(customers.isEmpty()).isTrue();
    }

    @Test
    void 중복되는_전화번호를_등록하면_예외가_발생한다() {
        RegisterCustomer customer = REGISTER_CUSTOMER_GITCHAN;
        RegisterCustomer savedCustomer = customerRepository.save(customer);
        String phoneNumber = savedCustomer.getPhoneNumber();

        RegisterCustomer duplicatePhoneNumberCustomer = new RegisterCustomer("전화번호_중복쟁이", phoneNumber, "loginId", "password");
        assertThatThrownBy(() -> customerRepository.save(duplicatePhoneNumberCustomer))
                .isInstanceOf(DataIntegrityViolationException.class);
    }
}
