package com.stampcrush.backend.application.coupon;

import com.stampcrush.backend.application.coupon.dto.CustomerCouponFindResultsDto;
import com.stampcrush.backend.entity.coupon.Coupon;
import com.stampcrush.backend.entity.coupon.CouponStatus;
import com.stampcrush.backend.entity.user.Customer;
import com.stampcrush.backend.exception.CustomerNotFoundException;
import com.stampcrush.backend.repository.coupon.CouponRepository;
import com.stampcrush.backend.repository.user.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CustomerCouponFindService {

    private final CustomerRepository customerRepository;
    private final CouponRepository couponRepository;

    public List<CustomerCouponFindResultsDto> findCouponPerCafe(Long customerId) {
        Optional<Customer> findCustomer = customerRepository.findById(customerId);

        if (findCustomer.isEmpty()) {
            throw new CustomerNotFoundException("해당 아이디의 고객을 찾을 수 없습니다. 로그인 해 주세요.");
        }

        Customer customer = findCustomer.get();

        List<Coupon> coupons = couponRepository.findByCustomerAndStatus(customer, CouponStatus.ACCUMULATING);

        for (Coupon coupon : coupons) {
            coupon.getCafe();
        }
        return null;
    }
}
