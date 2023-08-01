package com.stampcrush.backend.api.manager.customer;

import com.stampcrush.backend.config.resolver.OwnerAuth;
import com.stampcrush.backend.api.manager.customer.request.TemporaryCustomerCreateRequest;
import com.stampcrush.backend.api.manager.customer.response.CustomerFindResponse;
import com.stampcrush.backend.api.manager.customer.response.CustomersFindResponse;
import com.stampcrush.backend.application.customer.CustomerService;
import com.stampcrush.backend.application.customer.dto.CustomersFindResultDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/admin")
public class ManagerCustomerApiController {

    private final CustomerService customerService;

    @GetMapping("/customers")
    public ResponseEntity<CustomersFindResponse> findCustomer(OwnerAuth owner, @RequestParam("phone-number") String phoneNumber) {
        CustomersFindResultDto customers = customerService.findCustomer(phoneNumber);

        List<CustomerFindResponse> customerFindResponses = customers.getCustomer().stream()
                .map(CustomerFindResponse::from).collect(toList());

        return ResponseEntity.ok().body(new CustomersFindResponse(customerFindResponses));
    }

    @PostMapping("/temporary-customers")
    public ResponseEntity<Void> createTemporaryCustomer(OwnerAuth owner, @RequestBody @Valid TemporaryCustomerCreateRequest request) {
        Long customerId = customerService.createTemporaryCustomer(request.getPhoneNumber());

        return ResponseEntity.created(URI.create("/customers/" + customerId)).build();
    }
}
