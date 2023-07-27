package com.stampcrush.backend.repository.coupon;

import com.stampcrush.backend.entity.coupon.CouponDesign;
import com.stampcrush.backend.entity.coupon.CouponStampCoordinate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CouponStampCoordinateRepository extends JpaRepository<CouponStampCoordinate, Long> {

    List<CouponStampCoordinate> f회indByCouponDesign(CouponDesign couponDesign);
}
