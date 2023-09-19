package com.stampcrush.backend.application.manager.customer;

import com.stampcrush.backend.application.ServiceSliceTest;
import com.stampcrush.backend.application.manager.customer.dto.CustomerFindDto;
import com.stampcrush.backend.entity.user.Customer;
import com.stampcrush.backend.repository.user.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ServiceSliceTest
class ManagerCustomerFindServiceTest {

    @InjectMocks
    private ManagerCustomerFindService managerCustomerFindService;

    @Mock
    private CustomerRepository customerRepository;

    @Test
    void 전화번호로_가입_고객을_조회한다() {
        // given
        Customer customer = Customer.registeredCustomerBuilder()
                .nickname("제나")
                .phoneNumber("01012345678")
                .build();
        when(customerRepository.findByPhoneNumber(customer.getPhoneNumber())).thenReturn(Collections.singletonList(customer));

        // when
        List<CustomerFindDto> findCustomer = managerCustomerFindService.findCustomer("01012345678");

        // then
        assertThat(findCustomer).containsExactly(CustomerFindDto.from(customer));
    }

    @Test
    void 전화번호로_임시_고객을_조회한다() {
        // given
        Customer customer = Customer.temporaryCustomerBuilder()
                .phoneNumber("01012345678")
                .build();
        when(customerRepository.findByPhoneNumber(customer.getPhoneNumber())).thenReturn(Collections.singletonList(customer));

        // when
        List<CustomerFindDto> findCustomer = managerCustomerFindService.findCustomer("01012345678");

        // then
        assertThat(findCustomer).containsExactly(CustomerFindDto.from(customer));
    }

    @Test
    void 고객이_존재하지_않는_경우_빈_배열을_반환한다() {
        // given
        String notExistPhoneNumber = "01000000000";
        when(customerRepository.findByPhoneNumber(notExistPhoneNumber)).thenReturn(Collections.emptyList());

        // when
        List<CustomerFindDto> findCustomer = managerCustomerFindService.findCustomer(notExistPhoneNumber);

        assertThat(findCustomer.size()).isEqualTo(0);
    }
}
