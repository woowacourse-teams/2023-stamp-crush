package com.stampcrush.backend.api.cafe;

import com.stampcrush.backend.api.dto.request.CafeCreateRequest;
import com.stampcrush.backend.api.dto.response.CafeFindResponse;
import com.stampcrush.backend.api.dto.response.CafesFindResponse;
import com.stampcrush.backend.application.cafe.CafeService;
import com.stampcrush.backend.application.cafe.dto.CafeCreate;
import com.stampcrush.backend.application.cafe.dto.CafeFindResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class CafeApiController {

    private final CafeService cafeService;

    @GetMapping("/cafes")
    ResponseEntity<CafesFindResponse> findAllCafes() {
        List<CafeFindResult> cafeFindResults = cafeService.findAllCafes(1L);
        List<CafeFindResponse> cafeFindResponses = cafeFindResults.stream()
                .map(CafeFindResponse::from)
                .toList();
        CafesFindResponse response = new CafesFindResponse(cafeFindResponses);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/cafes")
    ResponseEntity<Void> createCafe(@RequestBody CafeCreateRequest cafeCreateRequest) {
        CafeCreate cafeCreate = new CafeCreate(
                1L,
                cafeCreateRequest.getName(),
                cafeCreateRequest.getOpenTime(),
                cafeCreateRequest.getCloseTime(),
                cafeCreateRequest.getTelephoneNumber(),
                cafeCreateRequest.getCafeImageUrl(),
                cafeCreateRequest.getRoadAddress(),
                cafeCreateRequest.getDetailAddress(),
                cafeCreateRequest.getBusinessRegistrationNumber());
        Long cafeId = cafeService.createCafe(cafeCreate);
        return ResponseEntity.created(URI.create("/cafes/" + cafeId)).build();
    }
}
