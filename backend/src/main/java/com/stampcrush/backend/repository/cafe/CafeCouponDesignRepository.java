package com.stampcrush.backend.repository.cafe;

import com.stampcrush.backend.entity.cafe.Cafe;
import com.stampcrush.backend.entity.cafe.CafeCouponDesign;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CafeCouponDesignRepository extends JpaRepository<CafeCouponDesign, Long> {

    Optional<CafeCouponDesign> findByCafeAndDeletedIsFalse(Cafe cafe);
}
