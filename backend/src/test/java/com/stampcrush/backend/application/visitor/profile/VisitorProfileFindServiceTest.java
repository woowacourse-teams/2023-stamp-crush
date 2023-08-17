package com.stampcrush.backend.application.visitor.profile;

import com.stampcrush.backend.application.ServiceSliceTest;
import com.stampcrush.backend.repository.user.RegisterCustomerRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

@ServiceSliceTest
class VisitorProfileFindServiceTest {

    @InjectMocks
    private VisitorProfileFindService visitorProfileFindService;

    @Mock
    private RegisterCustomerRepository customerRepository;

    @Test
    void 고객의_프로필_정보를_조회한다() {
        // given

    }
}
