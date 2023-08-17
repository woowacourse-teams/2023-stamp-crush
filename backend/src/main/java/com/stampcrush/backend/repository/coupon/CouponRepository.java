package com.stampcrush.backend.repository.coupon;

import com.stampcrush.backend.entity.cafe.Cafe;
import com.stampcrush.backend.entity.coupon.Coupon;
import com.stampcrush.backend.entity.coupon.CouponStatus;
import com.stampcrush.backend.entity.user.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CouponRepository extends JpaRepository<Coupon, Long> {

    @Query("select distinct cp from Coupon cp left join fetch cp.stamps join fetch cp.cafe join fetch cp.customer where cp.cafe = :cafe")
    List<Coupon> findByCafe(@Param("cafe") Cafe cafe);

    List<Coupon> findByCafeAndCustomerAndStatus(@Param("cafe") Cafe cafe, @Param("customer") Customer customer, @Param("status") CouponStatus status);

    @Query("SELECT c FROM Coupon c LEFT JOIN Favorites f ON c.cafe.id = f.cafe.id WHERE c.customer = :customer AND c.status = :status ORDER BY f.isFavorites DESC, c.expiredDate ASC")
    List<Coupon> findFilteredAndSortedCoupons(@Param("customer") Customer customer, @Param("status") CouponStatus status);

    Optional<Coupon> findByIdAndCustomerId(Long id, Long customerId);
}
