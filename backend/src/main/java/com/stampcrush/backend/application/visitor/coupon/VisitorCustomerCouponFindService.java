package com.stampcrush.backend.application.visitor.coupon;

import com.stampcrush.backend.application.visitor.coupon.dto.CustomerCouponFindResultDto;
import com.stampcrush.backend.entity.cafe.Cafe;
import com.stampcrush.backend.entity.coupon.Coupon;
import com.stampcrush.backend.entity.coupon.CouponStampCoordinate;
import com.stampcrush.backend.entity.coupon.CouponStatus;
import com.stampcrush.backend.entity.user.Customer;
import com.stampcrush.backend.exception.CustomerNotFoundException;
import com.stampcrush.backend.repository.coupon.CouponRepository;
import com.stampcrush.backend.repository.coupon.CouponStampCoordinateRepository;
import com.stampcrush.backend.repository.user.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class VisitorCustomerCouponFindService {

    private static final Boolean IS_FAVORITES_TEMPORARY_VALUE = Boolean.TRUE;

    private final CustomerRepository customerRepository;
    private final CouponRepository couponRepository;
    private final CouponStampCoordinateRepository couponStampCoordinateRepository;

    public List<CustomerCouponFindResultDto> findOneCouponForOneCafe(Long customerId) {
        Customer customer = findExistingCustomer(customerId);
        List<Coupon> coupons = couponRepository.findByCustomerAndStatus(customer, CouponStatus.ACCUMULATING);

        List<CustomerCouponFindResultDto> response = new ArrayList<>();
        for (Coupon coupon : coupons) {
            Cafe cafe = coupon.getCafe();
            Boolean isFavorites = IS_FAVORITES_TEMPORARY_VALUE;
            List<CouponStampCoordinate> coordinates = couponStampCoordinateRepository.findByCouponDesign(coupon.getCouponDesign());
            response.add(CustomerCouponFindResultDto.of(cafe, coupon, isFavorites, coordinates));
        }
        return response;
    }

    private Customer findExistingCustomer(Long customerId) {
        Optional<Customer> findCustomer = customerRepository.findById(customerId);

        if (findCustomer.isEmpty()) {
            throw new CustomerNotFoundException("해당 id의 고객을 찾을 수 없습니다. 회원가입 해 주세요.");
        }

        return findCustomer.get();
    }
}
