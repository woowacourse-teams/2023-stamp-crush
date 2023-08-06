package com.stampcrush.backend.repository.user;

import com.stampcrush.backend.entity.user.Customer;
import com.stampcrush.backend.entity.user.TemporaryCustomer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

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
    void findByPhoneNumber_존재하지_않는_전화번호로_고객을_조회하면_빈_리스트를_반환한다() {
        // given
        String notExistPhoneNumber = "01012345678";

        // when
        List<Customer> customers = customerRepository.findByPhoneNumber(notExistPhoneNumber);

        // then
        assertThat(customers.isEmpty()).isTrue();
    }
}
