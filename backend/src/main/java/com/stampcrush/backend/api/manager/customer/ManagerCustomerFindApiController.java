package com.stampcrush.backend.api.manager.customer;

import com.stampcrush.backend.api.manager.coupon.response.CafeCustomerFindResponse;
import com.stampcrush.backend.api.manager.coupon.response.CafeCustomersFindResponse;
import com.stampcrush.backend.api.manager.customer.response.CustomerFindResponse;
import com.stampcrush.backend.api.manager.customer.response.CustomersFindResponse;
import com.stampcrush.backend.application.manager.coupon.dto.CafeCustomerFindResultDto;
import com.stampcrush.backend.application.manager.customer.ManagerCustomerFindService;
import com.stampcrush.backend.application.manager.customer.dto.CustomerFindDto;
import com.stampcrush.backend.config.resolver.OwnerAuth;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/admin")
public class ManagerCustomerFindApiController {

    private final ManagerCustomerFindService managerCustomerFindService;

    @GetMapping("/customers")
    public ResponseEntity<CustomersFindResponse> findCustomer(
            OwnerAuth owner,
            @RequestParam("phone-number") String phoneNumber
    ) {
        List<CustomerFindDto> customers = managerCustomerFindService.findCustomer(phoneNumber);

        List<CustomerFindResponse> customerFindResponses = customers.stream()
                .map(CustomerFindResponse::from).collect(toList());

        return ResponseEntity.ok().body(new CustomersFindResponse(customerFindResponses));
    }

    @GetMapping("/cafes/{cafeId}/customers")
    public ResponseEntity<CafeCustomersFindResponse> findCustomersByCafe(
            OwnerAuth owner,
            @PathVariable("cafeId") Long cafeId
    ) {
        List<CafeCustomerFindResultDto> coupons = managerCustomerFindService.findCouponsByCafe(owner.getId(), cafeId);
        List<CafeCustomerFindResponse> cafeCustomerFindResponses = coupons.stream()
                .map(CafeCustomerFindResponse::from)
                .toList();

        return ResponseEntity.ok(new CafeCustomersFindResponse(cafeCustomerFindResponses));
    }
}
