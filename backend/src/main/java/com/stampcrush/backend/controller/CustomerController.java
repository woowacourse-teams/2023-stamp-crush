package com.stampcrush.backend.controller;

import com.stampcrush.backend.service.CustomerResponse;
import com.stampcrush.backend.service.CustomerService;
import com.stampcrush.backend.service.TemporaryCustomerRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/customers")
    public ResponseEntity<List<CustomerResponse>> findCustomer(@RequestParam("phone-number") String phoneNumber) {
        List<CustomerResponse> customers = customerService.findCustomer(phoneNumber);
        return ResponseEntity.ok().body(customers);
    }

    @PostMapping("temporary-customers")
    public ResponseEntity<Void> createTemporaryCustomer(@RequestBody @Valid TemporaryCustomerRequest temporaryCustomerRequest) {
        Long customerId = customerService.createTemporaryCustomer(temporaryCustomerRequest.getPhoneNumber());

        return ResponseEntity.created(URI.create("/customers/" + customerId)).build();
    }
}
