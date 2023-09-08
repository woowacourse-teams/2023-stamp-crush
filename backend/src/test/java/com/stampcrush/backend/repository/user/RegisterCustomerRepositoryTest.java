package com.stampcrush.backend.repository.user;

import com.stampcrush.backend.auth.OAuthProvider;
import com.stampcrush.backend.common.KorNamingConverter;
import com.stampcrush.backend.entity.user.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@KorNamingConverter
@DataJpaTest
class RegisterCustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    void 주어진_LoginId에_해당하는_고객을_조회한다() {
        // given
        String loginId = "jenaId";

        Customer registerCustomer = customerRepository.save(Customer.registeredCustomerBuilder()
                .nickname("jena")
                .phoneNumber("01012345678")
                .loginId(loginId)
                .build());
        Customer savedCustomer = customerRepository.save(registerCustomer);

        // when
        Optional<Customer> findCustomer = customerRepository.findByLoginId(loginId);

        // then
        assertThat(findCustomer.get()).isEqualTo(savedCustomer);
    }

    @Test
    void 존재하지_않는_LoginId로_고객을_조회하면_빈_Optional을_반환한다() {
        // given
        String notExistLoginId = "notExist";

        // when
        Optional<Customer> findCustomer = customerRepository.findByLoginId(notExistLoginId);

        // then
        assertThat(findCustomer).isEmpty();
    }

    @Test
    void OAuthProvider와_OAuthId로_고객을_조회한다() {
        long oAuthId = 123L;
        OAuthProvider oauthProvider = OAuthProvider.KAKAO;

        Customer customer = Customer.registeredCustomerBuilder()
                .nickname("제나")
                .email("yenawee@naver.com")
                .oAuthProvider(oauthProvider)
                .oAuthId(oAuthId)
                .build();

        Customer savedCustomer = customerRepository.save(customer);
        Customer findCustomer = customerRepository.findByOAuthProviderAndOAuthId(oauthProvider, oAuthId).get();

        assertThat(savedCustomer).isEqualTo(findCustomer);
    }
}
