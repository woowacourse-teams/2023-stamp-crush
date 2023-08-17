package com.stampcrush.backend.repository.coupon;

import com.stampcrush.backend.entity.coupon.Stamp;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StampRepository extends JpaRepository<Stamp, Long> {
}
