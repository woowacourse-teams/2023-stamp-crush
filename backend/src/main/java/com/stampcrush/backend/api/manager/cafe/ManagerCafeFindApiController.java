package com.stampcrush.backend.api.manager.cafe;

import com.stampcrush.backend.api.manager.cafe.response.CafeFindResponse;
import com.stampcrush.backend.api.manager.cafe.response.CafesFindResponse;
import com.stampcrush.backend.application.manager.cafe.ManagerCafeFindService;
import com.stampcrush.backend.application.manager.cafe.dto.CafeFindResultDto;
import com.stampcrush.backend.config.resolver.OwnerAuth;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/admin/cafes")
public class ManagerCafeFindApiController {

    private final ManagerCafeFindService managerCafeFindService;

    @GetMapping
    ResponseEntity<CafesFindResponse> findAllCafes(OwnerAuth owner) {
        List<CafeFindResultDto> cafeFindResultDtos = managerCafeFindService.findCafesByOwner(owner.getId());
        List<CafeFindResponse> cafeFindResponses = cafeFindResultDtos.stream()
                .map(CafeFindResponse::from)
                .toList();
        CafesFindResponse response = new CafesFindResponse(cafeFindResponses);
        return ResponseEntity.ok(response);
    }
}
