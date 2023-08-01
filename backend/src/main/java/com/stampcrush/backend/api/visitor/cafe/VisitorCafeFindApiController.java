package com.stampcrush.backend.api.visitor.cafe;

import com.stampcrush.backend.api.visitor.cafe.response.CafeInfoFindByCustomerResponse;
import com.stampcrush.backend.api.visitor.cafe.response.CafeInfoFindResponse;
import com.stampcrush.backend.application.visitor.cafe.VisitorCafeFindService;
import com.stampcrush.backend.application.visitor.cafe.dto.CafeInfoFindByCustomerResultDto;
import com.stampcrush.backend.config.resolver.CustomerAuth;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/cafes")
public class VisitorCafeFindApiController {

    private final VisitorCafeFindService visitorCafeFindService;

    @GetMapping("/{cafeId}")
    ResponseEntity<CafeInfoFindByCustomerResponse> findCafe(CustomerAuth customer, @PathVariable Long cafeId) {
        CafeInfoFindByCustomerResultDto cafeInfoFindByCustomerResultDto = visitorCafeFindService.findCafeById(cafeId);
        CafeInfoFindResponse response = CafeInfoFindResponse.from(cafeInfoFindByCustomerResultDto);
        return ResponseEntity.ok().body(new CafeInfoFindByCustomerResponse(response));
    }
}
