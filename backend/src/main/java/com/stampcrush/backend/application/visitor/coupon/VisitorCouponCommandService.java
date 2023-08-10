package com.stampcrush.backend.application.visitor.coupon;

import com.stampcrush.backend.entity.coupon.Coupon;
import com.stampcrush.backend.exception.CouponNotFoundException;
import com.stampcrush.backend.repository.coupon.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class VisitorCouponCommandService {

    private final CouponRepository couponRepository;

    public void deleteCoupon(Long customerId, Long couponId) {
        Coupon coupon = couponRepository.findByIdAndCustomerId(couponId, customerId)
                .orElseThrow(() -> new CouponNotFoundException("쿠폰을 찾을 수 없습니다."));
        coupon.delete();
    }
}
