package com.stampcrush.backend.application.manager.customer;

import com.stampcrush.backend.application.ServiceSliceTest;
import com.stampcrush.backend.application.manager.customer.dto.CustomerFindDto;
import com.stampcrush.backend.common.KorNamingConverter;
import com.stampcrush.backend.entity.user.Customer;
import com.stampcrush.backend.entity.user.RegisterCustomer;
import com.stampcrush.backend.entity.user.TemporaryCustomer;
import com.stampcrush.backend.repository.user.CustomerRepository;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
        Customer customer = new RegisterCustomer("제나", "01012345678", "jena", "1234");
        when(customerRepository.findByPhoneNumber(customer.getPhoneNumber())).thenReturn(Collections.singletonList(customer));

        // when
        List<CustomerFindDto> findCustomer = managerCustomerFindService.findCustomer("01012345678");

        // then
        assertThat(findCustomer).containsExactly(CustomerFindDto.from(customer));
    }

    @Test
    void 전화번호로_임시_고객을_조회한다() {
        // given
        Customer customer = TemporaryCustomer.from("01012345678");
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
