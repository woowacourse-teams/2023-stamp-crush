package com.stampcrush.backend.application.visitor.profile;

import com.stampcrush.backend.application.ServiceSliceTest;
import com.stampcrush.backend.application.visitor.profile.dto.VisitorProfileFindResultDto;
import com.stampcrush.backend.repository.user.RegisterCustomerRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;

import static com.stampcrush.backend.fixture.CustomerFixture.REGISTER_CUSTOMER_JENA;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ServiceSliceTest
class VisitorProfileFindServiceTest {

    @InjectMocks
    private VisitorProfileFindService visitorProfileFindService;

    @Mock
    private RegisterCustomerRepository customerRepository;

    @Test
    void 고객의_프로필_정보를_조회한다() {
        // given
        when(customerRepository.findById(anyLong())).thenReturn(Optional.of(REGISTER_CUSTOMER_JENA));

        // when
        VisitorProfileFindResultDto expected = new VisitorProfileFindResultDto(REGISTER_CUSTOMER_JENA.getId(),
                REGISTER_CUSTOMER_JENA.getNickname(),
                REGISTER_CUSTOMER_JENA.getPhoneNumber(),
                REGISTER_CUSTOMER_JENA.getEmail());
        VisitorProfileFindResultDto result = visitorProfileFindService.findProfile(1L);

        // then
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);
    }
}
