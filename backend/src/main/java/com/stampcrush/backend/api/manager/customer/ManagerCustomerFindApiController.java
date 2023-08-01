package com.stampcrush.backend.api.manager.customer;

import com.stampcrush.backend.api.manager.customer.response.CustomerFindResponse;
import com.stampcrush.backend.api.manager.customer.response.CustomersFindResponse;
import com.stampcrush.backend.application.manager.customer.CustomerService;
import com.stampcrush.backend.application.manager.customer.dto.CustomersFindResultDto;
import com.stampcrush.backend.config.resolver.OwnerAuth;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/admin")
public class ManagerCustomerFindApiController {

    private final CustomerService customerService;

    @GetMapping("/customers")
    public ResponseEntity<CustomersFindResponse> findCustomer(OwnerAuth owner, @RequestParam("phone-number") String phoneNumber) {
        CustomersFindResultDto customers = customerService.findCustomer(phoneNumber);

        List<CustomerFindResponse> customerFindResponses = customers.getCustomer().stream()
                .map(CustomerFindResponse::from).collect(toList());

        return ResponseEntity.ok().body(new CustomersFindResponse(customerFindResponses));
    }
}
