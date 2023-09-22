package com.stampcrush.backend.repository.user;

import com.stampcrush.backend.common.KorNamingConverter;
import com.stampcrush.backend.entity.user.Customer;
import com.stampcrush.backend.fixture.CustomerFixture;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@KorNamingConverter
@DataJpaTest
class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    void 주어진_전화번호에_해당하는_고객을_조회한다() {
        // given
        String phoneNumber = "01012345678";
        Customer customer = Customer.temporaryCustomerBuilder()
                .phoneNumber(phoneNumber)
                .build();
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
        assertThat(customers).isEmpty();
    }

    @Test
    void 회원_삭제() {
        final Customer savedCustomer = customerRepository.save(CustomerFixture.REGISTER_CUSTOMER_GITCHAN_SAVED);
        final Optional<Customer> customerBeforeDelete = customerRepository.findById(savedCustomer.getId());

        customerRepository.deleteCustomerById(savedCustomer.getId());
        final Optional<Customer> customerAfterDelete = customerRepository.findById(savedCustomer.getId());

        assertAll(
                () -> assertThat(customerBeforeDelete).isNotEmpty(),
                () -> assertThat(customerBeforeDelete.get().getId()).isEqualTo(savedCustomer.getId()),
                () -> assertThat(customerAfterDelete).isEmpty()
        );
    }
}
