package com.stampcrush.backend.api.cafe;

import com.stampcrush.backend.api.cafe.response.CafeFindByCustomerResponse;
import com.stampcrush.backend.application.cafe.CafeService;
import com.stampcrush.backend.application.cafe.dto.CafeFindByCustomerResultDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/cafes")
public class CafeController {

    private final CafeService cafeService;

    @GetMapping("/{cafeId}")
    ResponseEntity<CafeFindByCustomerResponse> findCafe(@PathVariable Long cafeId) {
        CafeFindByCustomerResultDto cafeFindByCustomerResultDto = cafeService.findCafeById(cafeId);
        CafeFindByCustomerResponse response = CafeFindByCustomerResponse.from(cafeFindByCustomerResultDto);
        return ResponseEntity.ok(response);
    }
}
