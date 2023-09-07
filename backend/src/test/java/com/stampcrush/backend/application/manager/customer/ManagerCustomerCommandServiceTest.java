package com.stampcrush.backend.application.manager.customer;

import com.stampcrush.backend.application.ServiceSliceTest;
import com.stampcrush.backend.entity.user.Customer;
import com.stampcrush.backend.exception.CustomerBadRequestException;
import com.stampcrush.backend.repository.user.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ServiceSliceTest
class ManagerCustomerCommandServiceTest {

    @InjectMocks
    private ManagerCustomerCommandService managerCustomerCommandService;

    @Mock
    private CustomerRepository customerRepository;

    @Test
    void 임시_고객을_생성한다() {
        // given
        Customer temporaryCustomer = Customer.temporaryCustomerBuilder()
                .phoneNumber("01012345678")
                .build();
        when(customerRepository.findByPhoneNumber(temporaryCustomer.getPhoneNumber())).thenReturn(Collections.emptyList());
        when(customerRepository.save(any(Customer.class))).thenReturn(temporaryCustomer);

        // when
        Long customerId = managerCustomerCommandService.createTemporaryCustomer(temporaryCustomer.getPhoneNumber());

        // then
        verify(customerRepository, times(1)).save(any(Customer.class));
    }

    @Test
    void 존재하는_회원의_번호로_고객을_생성하려면_에러를_발생한다() {
        // given
        Customer temporaryCustomer = Customer.temporaryCustomerBuilder()
                .phoneNumber("01012345678")
                .build();
        when(customerRepository.findByPhoneNumber(temporaryCustomer.getPhoneNumber())).thenReturn(List.of(Customer.temporaryCustomerBuilder()
                .phoneNumber("01012345678")
                .build()));

        // when, then
        assertThatThrownBy(() -> managerCustomerCommandService.createTemporaryCustomer(temporaryCustomer.getPhoneNumber()))
                .isInstanceOf(CustomerBadRequestException.class);
    }
}
