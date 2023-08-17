package com.stampcrush.backend.application.visitor.profile;

import com.stampcrush.backend.application.ServiceSliceTest;
import com.stampcrush.backend.fixture.CustomerFixture;
import com.stampcrush.backend.repository.user.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ServiceSliceTest
class VisitorProfilesCommandServiceTest {

    @InjectMocks
    private VisitorProfilesCommandService visitorProfilesCommandService;

    @Mock
    private CustomerRepository customerRepository;

    @Test
    void 고객의_전화번호를_저장한다() {
        given(customerRepository.findById(any()))
                .willReturn(Optional.of(CustomerFixture.REGISTER_CUSTOMER_GITCHAN));

        assertDoesNotThrow(() -> visitorProfilesCommandService.registerPhoneNumber(any(), "01012345678"));
    }
}
