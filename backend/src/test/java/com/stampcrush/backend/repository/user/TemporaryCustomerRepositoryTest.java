package com.stampcrush.backend.repository.user;

import com.stampcrush.backend.entity.user.Customer;
import com.stampcrush.backend.entity.user.TemporaryCustomer;
import com.stampcrush.backend.fixture.CustomerFixture;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class TemporaryCustomerRepositoryTest {

    @Autowired
    private TemporaryCustomerRepository temporaryCustomerRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    void 임시_회원_삭제() {
        TemporaryCustomer savedCustomer = temporaryCustomerRepository.save(CustomerFixture.TEMPORARY_CUSTOMER_1);

        temporaryCustomerRepository.delete(savedCustomer);
        Optional<Customer> findCustomer = customerRepository.findById(savedCustomer.getId());

        assertThat(findCustomer).isEmpty();
    }
}
