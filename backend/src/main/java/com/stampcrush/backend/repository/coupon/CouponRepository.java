package com.stampcrush.backend.repository.coupon;

import com.stampcrush.backend.entity.cafe.Cafe;
import com.stampcrush.backend.entity.coupon.Coupon;
import com.stampcrush.backend.entity.coupon.CouponStatus;
import com.stampcrush.backend.entity.user.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CouponRepository extends JpaRepository<Coupon, Long> {

    @Query("select distinct cp from Coupon cp left join fetch cp.stamps join fetch cp.cafe join fetch cp.customer where cp.cafe = :cafe")
    List<Coupon> findByCafe(@Param("cafe") Cafe cafe);

    List<Coupon> findByCafeAndCustomerAndStatus(@Param("cafe") Cafe cafe, @Param("customer") Customer customer, @Param("status") CouponStatus status);
}
