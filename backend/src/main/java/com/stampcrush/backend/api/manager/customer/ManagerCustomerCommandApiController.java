package com.stampcrush.backend.api.manager.customer;

import com.stampcrush.backend.api.manager.customer.request.TemporaryCustomerCreateRequest;
import com.stampcrush.backend.application.manager.customer.CustomerService;
import com.stampcrush.backend.config.resolver.OwnerAuth;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/admin")
public class ManagerCustomerCommandApiController {

    private final CustomerService customerService;

    @PostMapping("/temporary-customers")
    public ResponseEntity<Void> createTemporaryCustomer(OwnerAuth owner, @RequestBody @Valid TemporaryCustomerCreateRequest request) {
        Long customerId = customerService.createTemporaryCustomer(request.getPhoneNumber());

        return ResponseEntity.created(URI.create("/customers/" + customerId)).build();
    }
}
