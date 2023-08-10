package com.stampcrush.backend.api.visitor.visithistory;


import com.stampcrush.backend.api.visitor.visithistory.response.CustomerStampHistoriesFindResponse;
import com.stampcrush.backend.api.visitor.visithistory.response.CustomerStampHistoryFindResponse;
import com.stampcrush.backend.application.visitor.visithistory.VisitorVisitHistoryFindService;
import com.stampcrush.backend.application.visitor.visithistory.dto.CustomerStampHistoryFindResultDto;
import com.stampcrush.backend.config.resolver.CustomerAuth;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class VisitorVisitHistoryFindApiController {

    private final VisitorVisitHistoryFindService visitorVisitHistoryFindService;

    @GetMapping("/stamp-histories")
    public ResponseEntity<CustomerStampHistoriesFindResponse> findStampHistoriesByCustomer(CustomerAuth customer) {
        List<CustomerStampHistoryFindResultDto> stampHistories = visitorVisitHistoryFindService.findStampHistoriesByCustomer(customer.getId());
        List<CustomerStampHistoryFindResponse> stampHistoryFindResponses = stampHistories.stream()
                .map(CustomerStampHistoryFindResponse::from)
                .toList();

        return ResponseEntity.ok(new CustomerStampHistoriesFindResponse(stampHistoryFindResponses));
    }
}
