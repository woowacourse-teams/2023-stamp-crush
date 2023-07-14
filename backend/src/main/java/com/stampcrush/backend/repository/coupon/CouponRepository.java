package com.stampcrush.backend.repository.coupon;

import com.stampcrush.backend.entity.coupon.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponRepository extends JpaRepository<Coupon, Long> {
}
