package com.stampcrush.backend.api.customer;

import com.stampcrush.backend.api.customer.request.TemporaryCustomerCreateRequest;
import com.stampcrush.backend.api.customer.response.CustomersFindResponse;
import com.stampcrush.backend.application.customer.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RequiredArgsConstructor
@RestController
public class CustomerApiController {

    private final CustomerService customerService;

    @GetMapping("/customers")
    public ResponseEntity<CustomersFindResponse> findCustomer(@RequestParam("phone-number") String phoneNumber) {
        CustomersFindResponse customers = customerService.findCustomer(phoneNumber);

        return ResponseEntity.ok().body(customers);
    }

    @PostMapping("/temporary-customers")
    public ResponseEntity<Void> createTemporaryCustomer(@RequestBody @Valid TemporaryCustomerCreateRequest temporaryCustomerCreateRequest) {
        Long customerId = customerService.createTemporaryCustomer(temporaryCustomerCreateRequest.getPhoneNumber());

        return ResponseEntity.created(URI.create("/customers/" + customerId)).build();
    }
}
