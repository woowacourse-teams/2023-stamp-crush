package com.stampcrush.backend.api.cafe;

import com.stampcrush.backend.api.dto.request.CafeCreateRequest;
import com.stampcrush.backend.application.cafe.CafeService;
import com.stampcrush.backend.application.cafe.dto.CafeCreate;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RequiredArgsConstructor
@RestController
public class CafeApiController {

    private final CafeService cafeService;

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
        return ResponseEntity.created(URI.create("/cafes" + cafeId)).build();
    }
}
