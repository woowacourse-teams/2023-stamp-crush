package com.stampcrush.backend.api.cafe;

import com.stampcrush.backend.api.cafe.response.CafeInfoFindByCustomerResponse;
import com.stampcrush.backend.api.cafe.response.CafeInfoFindResponse;
import com.stampcrush.backend.application.cafe.CafeService;
import com.stampcrush.backend.application.cafe.dto.CafeInfoFindByCustomerResultDto;
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
    ResponseEntity<CafeInfoFindByCustomerResponse> findCafe(@PathVariable Long cafeId) {
        CafeInfoFindByCustomerResultDto cafeInfoFindByCustomerResultDto = cafeService.findCafeById(cafeId);
        CafeInfoFindResponse response = CafeInfoFindResponse.from(cafeInfoFindByCustomerResultDto);
        return ResponseEntity.ok().body(new CafeInfoFindByCustomerResponse(response));
    }
}
