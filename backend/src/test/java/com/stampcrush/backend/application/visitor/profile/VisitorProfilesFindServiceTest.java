package com.stampcrush.backend.application.visitor.profile;

import com.stampcrush.backend.application.ServiceSliceTest;
import com.stampcrush.backend.application.visitor.profile.dto.VisitorProfileFindByPhoneNumberResultDto;
import com.stampcrush.backend.entity.user.RegisterCustomer;
import com.stampcrush.backend.exception.StampCrushException;
import com.stampcrush.backend.fixture.CustomerFixture;
import com.stampcrush.backend.repository.user.CustomerRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@ServiceSliceTest
class VisitorProfilesFindServiceTest {

    @InjectMocks
    private VisitorProfilesFindService visitorProfilesFindService;

    @Mock
    private CustomerRepository customerRepository;

//    @Mock
//    private RegisterCustomerRepository registerCustomerRepository;

    @Test
    void 전화번호로_사용자_정상_조회() {
        RegisterCustomer customer = CustomerFixture.REGISTER_CUSTOMER_GITCHAN;

        given(customerRepository.findByPhoneNumber(anyString()))
                .willReturn(List.of(customer));

        assertThat(visitorProfilesFindService.findCustomerProfileByNumber("01038626099"))
                .usingRecursiveComparison()
                .isEqualTo(VisitorProfileFindByPhoneNumberResultDto.from(customer));
    }

    @Test
    void 같은_전화번호의_사용자가_이미_2명_이상_존재하면_예외_발생() {
        given(customerRepository.findByPhoneNumber(anyString()))
                .willReturn(List.of(CustomerFixture.TEMPORARY_CUSTOMER_1, CustomerFixture.TEMPORARY_CUSTOMER_2));

        Assertions.assertThatThrownBy(() -> visitorProfilesFindService.findCustomerProfileByNumber("01038626099"))
                .isInstanceOf(StampCrushException.class);
    }

    @Test
    void 전화번호로_사용자_조회되지_않음() {
        given(customerRepository.findByPhoneNumber(anyString()))
                .willReturn(List.of());

        assertThat(visitorProfilesFindService.findCustomerProfileByNumber("01038626099"))
                .usingRecursiveComparison()
                .isEqualTo(VisitorProfileFindByPhoneNumberResultDto.from(null));
    }
}
