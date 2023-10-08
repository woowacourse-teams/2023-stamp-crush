package com.stampcrush.backend.repository.cafe;

import com.stampcrush.backend.entity.cafe.CafeCouponDesign;
import com.stampcrush.backend.entity.cafe.CafeStampCoordinate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CafeStampCoordinateRepository extends JpaRepository<CafeStampCoordinate, Long> {

    List<CafeStampCoordinate> findByCafeCouponDesign(CafeCouponDesign couponDesign);
}
