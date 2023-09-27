package com.stampcrush.backend.api.manager.owner;

import com.stampcrush.backend.api.manager.owner.request.OwnerCreateRequest;
import com.stampcrush.backend.api.manager.owner.request.OwnerLoginRequest;
import com.stampcrush.backend.application.manager.owner.ManagerCommandService;
import com.stampcrush.backend.application.manager.owner.dto.OwnerCreateDto;
import com.stampcrush.backend.application.manager.owner.dto.OwnerLoginDto;
import com.stampcrush.backend.auth.api.response.AuthTokensResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/admin")
public class ManagerCommandApiController {

    private final ManagerCommandService managerCommandService;

    @PostMapping("/owners")
    ResponseEntity<Void> register(@RequestBody OwnerCreateRequest ownerCreateRequest) {
        managerCommandService.register(OwnerCreateDto.from(ownerCreateRequest));
        return ResponseEntity.status(HttpStatus.CREATED.value()).build();
    }

    @PostMapping("/login")
    ResponseEntity<AuthTokensResponse> login(@RequestBody OwnerLoginRequest ownerLoginRequest) {
        AuthTokensResponse token = managerCommandService.login(OwnerLoginDto.from(ownerLoginRequest));
        return ResponseEntity.ok(token);
    }
}
