package com.stampcrush.backend.application.customer;

import com.stampcrush.backend.api.manager.customer.request.TemporaryCustomerCreateRequest;
import com.stampcrush.backend.application.manager.customer.ManagerCustomerCommandService;
import com.stampcrush.backend.application.manager.customer.ManagerCustomerFindService;
import com.stampcrush.backend.application.manager.customer.dto.CustomerFindDto;
import com.stampcrush.backend.application.manager.customer.dto.CustomersFindResultDto;
import com.stampcrush.backend.entity.user.Customer;
import com.stampcrush.backend.entity.user.RegisterCustomer;
import com.stampcrush.backend.entity.user.TemporaryCustomer;
import com.stampcrush.backend.exception.CustomerBadRequestException;
import com.stampcrush.backend.repository.user.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Transactional
@SpringBootTest
class ManagerCustomerCommandServiceTest {

    @Autowired
    private ManagerCustomerFindService managerCustomerFindService;

    @Autowired
    private ManagerCustomerCommandService managerCustomerCommandService;

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    void 전화번호로_가입_고객을_조회한다() {
        // given
        Customer customer = new RegisterCustomer("제나", "01012345678", "jena", "1234");
        customerRepository.save(customer);

        // when
        CustomersFindResultDto findCustomer = managerCustomerFindService.findCustomer("01012345678");

        // then
        assertThat(findCustomer.getCustomer()).containsExactly(CustomerFindDto.from(customer));
    }

    @Test
    void 전화번호로_임시_고객을_조회한다() {
        // given
        Customer customer = TemporaryCustomer.from("01012345678");
        customerRepository.save(customer);

        // when
        CustomersFindResultDto findCustomer = managerCustomerFindService.findCustomer("01012345678");

        // then
        assertThat(findCustomer.getCustomer()).containsExactly(CustomerFindDto.from(customer));
    }

    @Test
    void 고객이_존재하지_않는_경우_빈_배열을_반환한다() {
        // given, when
        CustomersFindResultDto findCustomer = managerCustomerFindService.findCustomer("01012345678");

        assertThat(findCustomer.getCustomer().size()).isEqualTo(0);
    }

    @Test
    void 임시_고객을_생성한다() {
        // given
        TemporaryCustomerCreateRequest temporaryCustomerCreateRequest = new TemporaryCustomerCreateRequest("01012345678");

        // when
        Long customerId = managerCustomerCommandService.createTemporaryCustomer(temporaryCustomerCreateRequest.getPhoneNumber());

        // then
        assertThat(customerRepository.findById(customerId).get().getPhoneNumber()).isEqualTo(temporaryCustomerCreateRequest.getPhoneNumber());
    }

    @Test
    void 존재하는_회원의_번호로_고객을_생성하려면_에러를_발생한다() {
        // given
        Customer customer = TemporaryCustomer.from("01012345678");
        customerRepository.save(customer);
        TemporaryCustomerCreateRequest temporaryCustomerCreateRequest = new TemporaryCustomerCreateRequest(customer.getPhoneNumber());

        // when, then
        assertThatThrownBy(() -> managerCustomerCommandService.createTemporaryCustomer(temporaryCustomerCreateRequest.getPhoneNumber()))
                .isInstanceOf(CustomerBadRequestException.class);
    }
}
