package com.stampcrush.backend.api.manager.cafe;

import com.stampcrush.backend.config.resolver.OwnerAuth;
import com.stampcrush.backend.api.manager.cafe.request.CafeCreateRequest;
import com.stampcrush.backend.api.manager.cafe.request.CafeUpdateRequest;
import com.stampcrush.backend.api.manager.cafe.response.CafeFindResponse;
import com.stampcrush.backend.api.manager.cafe.response.CafesFindResponse;
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
public class ManagerCafeApiController {

    private final CafeService cafeService;

    @GetMapping("/{ownerId}")
    ResponseEntity<CafesFindResponse> findAllCafes(OwnerAuth owner, @PathVariable Long ownerId) {
        List<CafeFindResultDto> cafeFindResultDtos = cafeService.findCafesByOwner(ownerId);
        List<CafeFindResponse> cafeFindResponses = cafeFindResultDtos.stream()
                .map(CafeFindResponse::from)
                .toList();
        CafesFindResponse response = new CafesFindResponse(cafeFindResponses);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{ownerId}")
    ResponseEntity<Void> createCafe(OwnerAuth owner,
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
            @RequestBody @Valid CafeUpdateRequest request
    ) {
        CafeUpdateDto cafeUpdateDto = request.toServiceDto();

        cafeService.updateCafeInfo(cafeUpdateDto, cafeId);
        return ResponseEntity.ok().build();
    }
}
