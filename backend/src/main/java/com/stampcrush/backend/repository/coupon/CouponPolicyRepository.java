package com.stampcrush.backend.repository.coupon;

import com.stampcrush.backend.entity.coupon.CouponPolicy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponPolicyRepository extends JpaRepository<CouponPolicy, Long> {
}
