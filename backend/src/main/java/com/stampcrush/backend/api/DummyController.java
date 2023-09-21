package com.stampcrush.backend.api;

import com.stampcrush.backend.application.manager.coupon.ManagerCouponCommandService;
import com.stampcrush.backend.application.manager.coupon.dto.StampCreateDto;
import com.stampcrush.backend.application.manager.customer.ManagerCustomerCommandService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@Slf4j
public class DummyController {

    private final ManagerCustomerCommandService managerCustomerCommandService;
    private final ManagerCouponCommandService managerCouponCommandService;

    @PostMapping("/api/admin/dummy")
    public ResponseEntity<Void> inputDummy() {

        int phone = 10000000;
        String prefix = "010";
        for (int i = 0; i < 5000; i++) {
            phone += i;

            int stamp = (int) ((Math.random() * 10000) % 8) + 1;
            try {
                Long temporaryCustomerId = managerCustomerCommandService.createTemporaryCustomer(String.valueOf(prefix + phone));
                Long couponId = managerCouponCommandService.createCoupon(2L, 2L, temporaryCustomerId);
                managerCouponCommandService.createStamp(new StampCreateDto(2L, temporaryCustomerId, couponId, stamp));
                log.info("phone: {}, stamp: {} 완료", phone, stamp);
            } catch (Exception e) {
                log.info("{}", e);
                log.info("error {} {}", phone, stamp);
                continue;
            }
        }

        return ResponseEntity.ok().build();
    }
}
