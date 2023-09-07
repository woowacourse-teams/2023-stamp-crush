package com.stampcrush.backend.repository.user;

import com.stampcrush.backend.auth.OAuthProvider;
import com.stampcrush.backend.entity.user.RegisterCustomer;
import com.stampcrush.backend.entity.user.TemporaryCustomer;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class DataLinkTest {

    @Autowired
    private RegisterCustomerRepository registerCustomerRepository;

    @Autowired
    private TemporaryCustomerRepository temporaryCustomerRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private EntityManager em;

    @Test
    void name() {
        TemporaryCustomer temporaryCustomer = temporaryCustomerRepository.save(TemporaryCustomer.from("01012345678"));
        Long id2 = temporaryCustomer.getId();
        RegisterCustomer registerCustomer = registerCustomerRepository.save(
                RegisterCustomer.builder()
                        .nickname("레오")
                        .oAuthId(12334512L)
                        .oAuthProvider(OAuthProvider.KAKAO)
                        .build()
        );
        registerCustomer.registerPhoneNumber("01038626099");
        Long id300 = registerCustomer.getId();
        em.flush();

        jdbcTemplate.execute("update customer set dtype='register' where customer_id=" + id2);

        em.flush();
        em.clear();

        Optional<TemporaryCustomer> find = temporaryCustomerRepository.findById(id2);
        assertThat(find).isEmpty();
    }
}
