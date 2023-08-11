package com.stampcrush.backend.application.visitor.visithistory;

import com.stampcrush.backend.application.ServiceSliceTest;
import com.stampcrush.backend.exception.CustomerNotFoundException;
import com.stampcrush.backend.repository.user.CustomerRepository;
import com.stampcrush.backend.repository.visithistory.VisitHistoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

@ServiceSliceTest
class VisitorVisitHistoryFindServiceTest {

    @InjectMocks
    private VisitorVisitHistoryFindService visitorVisitHistoryFindService;

    @Mock
    private VisitHistoryRepository visitHistoryRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Test
    void 존재하지_않는_고객이_스탬프_적립_내역_조회하면_예외발생() {
        // given, when
        given(customerRepository.findById(anyLong()))
                .willReturn(Optional.empty());

        // then
        assertThatThrownBy(() -> visitorVisitHistoryFindService.findStampHistoriesByCustomer(1L))
                .isInstanceOf(CustomerNotFoundException.class);
    }
}
