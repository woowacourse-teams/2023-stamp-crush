package com.stampcrush.backend.api.cafe;

import com.stampcrush.backend.api.cafe.request.CafeCreateRequest;
import com.stampcrush.backend.api.cafe.response.CafeFindResponse;
import com.stampcrush.backend.api.cafe.response.CafesFindResponse;
import com.stampcrush.backend.application.cafe.CafeService;
import com.stampcrush.backend.application.cafe.dto.CafeCreateDto;
import com.stampcrush.backend.application.cafe.dto.CafeFindResultDto;
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
        List<CafeFindResultDto> cafeFindResultDtos = cafeService.findCafesByOwner(1L);
        List<CafeFindResponse> cafeFindResponses = cafeFindResultDtos.stream()
                .map(CafeFindResponse::from)
                .toList();
        CafesFindResponse response = new CafesFindResponse(cafeFindResponses);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/cafes")
    ResponseEntity<Void> createCafe(@RequestBody CafeCreateRequest cafeCreateRequest) {
        CafeCreateDto cafeCreateDto = new CafeCreateDto(
                1L,
                cafeCreateRequest.getName(),
                cafeCreateRequest.getRoadAddress(),
                cafeCreateRequest.getDetailAddress(),
                cafeCreateRequest.getBusinessRegistrationNumber());
        Long cafeId = cafeService.createCafe(cafeCreateDto);
        return ResponseEntity.created(URI.create("/cafes/" + cafeId)).build();
    }
}
