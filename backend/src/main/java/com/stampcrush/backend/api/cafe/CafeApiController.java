package com.stampcrush.backend.api.cafe;

import com.stampcrush.backend.api.cafe.request.CafeCreateRequest;
import com.stampcrush.backend.api.cafe.request.CafeUpdateRequest;
import com.stampcrush.backend.api.cafe.response.CafeFindResponse;
import com.stampcrush.backend.api.cafe.response.CafesFindResponse;
import com.stampcrush.backend.application.cafe.CafeService;
import com.stampcrush.backend.application.cafe.dto.CafeCreateDto;
import com.stampcrush.backend.application.cafe.dto.CafeFindResultDto;
import com.stampcrush.backend.application.cafe.dto.CafeUpdateDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/admin/cafes")
public class CafeApiController {

    private final CafeService cafeService;

    @GetMapping("/{ownerId}")
    ResponseEntity<CafesFindResponse> findAllCafes(@PathVariable Long ownerId) {
        List<CafeFindResultDto> cafeFindResultDtos = cafeService.findCafesByOwner(ownerId);
        List<CafeFindResponse> cafeFindResponses = cafeFindResultDtos.stream()
                .map(CafeFindResponse::from)
                .toList();
        CafesFindResponse response = new CafesFindResponse(cafeFindResponses);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{ownerId}")
    ResponseEntity<Void> createCafe(
            @PathVariable Long ownerId,
            @RequestBody @Valid CafeCreateRequest cafeCreateRequest
    ) {
        CafeCreateDto cafeCreateDto = new CafeCreateDto(
                ownerId,
                cafeCreateRequest.getName(),
                cafeCreateRequest.getRoadAddress(),
                cafeCreateRequest.getDetailAddress(),
                cafeCreateRequest.getBusinessRegistrationNumber());
        Long cafeId = cafeService.createCafe(cafeCreateDto);
        return ResponseEntity.created(URI.create("/cafes/" + cafeId)).build();
    }

    @PatchMapping("/{cafeId}")
    ResponseEntity<Void> updateCafe(
            @PathVariable Long cafeId,
            @RequestBody @Valid CafeUpdateRequest cafeUpdateRequest
    ) {
        CafeUpdateDto cafeUpdateDto = new CafeUpdateDto(
                cafeUpdateRequest.getOpenTime(),
                cafeUpdateRequest.getCloseTime(),
                cafeUpdateRequest.getTelephoneNumber(),
                cafeUpdateRequest.getCafeImageUrl()
        );

        cafeService.updateCafeInfo(cafeUpdateDto, cafeId);
        return ResponseEntity.ok().build();
    }
}
