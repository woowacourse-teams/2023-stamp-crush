package com.stampcrush.backend.application.visitor.profile;

import com.stampcrush.backend.application.ServiceSliceTest;
import com.stampcrush.backend.application.visitor.profile.dto.VisitorPhoneNumberFindResultDto;
import com.stampcrush.backend.repository.user.RegisterCustomerRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;

import static com.stampcrush.backend.fixture.CustomerFixture.REGISTER_CUSTOMER_JENA;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ServiceSliceTest
class VisitorProfileFindServiceTest {

    @InjectMocks
    private VisitorProfileFindService visitorProfileFindService;

    @Mock
    private RegisterCustomerRepository customerRepository;

    @Test
    void 고객의_전화번호가_등록되어_있으면_전화번호_반환() {
        // given
        when(customerRepository.findById(anyInt()))
                .thenReturn(Optional.of(REGISTER_CUSTOMER_JENA));

        // when
        VisitorPhoneNumberFindResultDto expected = new VisitorPhoneNumberFindResultDto(REGISTER_CUSTOMER_JENA.getPhoneNumber());
        VisitorPhoneNumberFindResultDto resultDto = visitorProfileFindService.findPhoneNumber(1L);

        // then
        assertThat(resultDto).usingRecursiveComparison().isEqualTo(expected);
    }
}
