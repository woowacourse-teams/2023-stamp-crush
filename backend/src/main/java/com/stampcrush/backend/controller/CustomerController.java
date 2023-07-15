package com.stampcrush.backend.controller;

import com.stampcrush.backend.service.CustomerResponse;
import com.stampcrush.backend.service.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
