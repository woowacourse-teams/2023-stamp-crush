package com.stampcrush.backend.application.manager.customer;

import com.stampcrush.backend.common.KorNamingConverter;
import com.stampcrush.backend.entity.user.TemporaryCustomer;
import com.stampcrush.backend.exception.CustomerBadRequestException;
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

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@KorNamingConverter
@ExtendWith(MockitoExtension.class)
class ManagerCustomerCommandServiceTest {

    @InjectMocks
    private ManagerCustomerCommandService managerCustomerCommandService;

    @Mock
    private CustomerRepository customerRepository;

    @Test
    void 임시_고객을_생성한다() {
        // given
        TemporaryCustomer temporaryCustomer = TemporaryCustomer.from("01012345678");
        when(customerRepository.findByPhoneNumber(temporaryCustomer.getPhoneNumber())).thenReturn(Collections.emptyList());
        when(customerRepository.save(any(TemporaryCustomer.class))).thenReturn(temporaryCustomer);

        // when
        Long customerId = managerCustomerCommandService.createTemporaryCustomer(temporaryCustomer.getPhoneNumber());

        // then
        verify(customerRepository, times(1)).save(any(TemporaryCustomer.class));
    }

    @Test
    void 존재하는_회원의_번호로_고객을_생성하려면_에러를_발생한다() {
        // given
        TemporaryCustomer temporaryCustomer = TemporaryCustomer.from("01012345678");
        when(customerRepository.findByPhoneNumber(temporaryCustomer.getPhoneNumber())).thenReturn(List.of(TemporaryCustomer.from("01012345678å")));

        // when, then
        assertThatThrownBy(() -> managerCustomerCommandService.createTemporaryCustomer(temporaryCustomer.getPhoneNumber()))
                .isInstanceOf(CustomerBadRequestException.class);
    }
}
