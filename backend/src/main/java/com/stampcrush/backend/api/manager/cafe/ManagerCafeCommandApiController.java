package com.stampcrush.backend.api.manager.cafe;

import com.stampcrush.backend.api.manager.cafe.request.CafeCreateRequest;
import com.stampcrush.backend.api.manager.cafe.request.CafeUpdateRequest;
import com.stampcrush.backend.application.manager.cafe.ManagerCafeCommandService;
import com.stampcrush.backend.application.manager.cafe.dto.CafeCreateDto;
import com.stampcrush.backend.application.manager.cafe.dto.CafeUpdateDto;
import com.stampcrush.backend.config.resolver.OwnerAuth;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/admin/cafes")
public class ManagerCafeCommandApiController {

    private final ManagerCafeCommandService managerCafeCommandService;

    @PostMapping("/{ownerId}")
    ResponseEntity<Void> createCafe(
            OwnerAuth owner,
            @PathVariable Long ownerId,
            @RequestBody @Valid CafeCreateRequest cafeCreateRequest
    ) {
        CafeCreateDto cafeCreateDto = new CafeCreateDto(
                ownerId,
                cafeCreateRequest.getName(),
                cafeCreateRequest.getRoadAddress(),
                cafeCreateRequest.getDetailAddress(),
                cafeCreateRequest.getBusinessRegistrationNumber());
        Long cafeId = managerCafeCommandService.createCafe(cafeCreateDto);
        return ResponseEntity.created(URI.create("/cafes/" + cafeId)).build();
    }

    @PatchMapping("/{cafeId}")
    ResponseEntity<Void> updateCafe(
            @PathVariable Long cafeId,
            @RequestBody @Valid CafeUpdateRequest request
    ) {
        CafeUpdateDto cafeUpdateDto = request.toServiceDto();

        managerCafeCommandService.updateCafeInfo(cafeUpdateDto, cafeId);
        return ResponseEntity.ok().build();
    }
}
