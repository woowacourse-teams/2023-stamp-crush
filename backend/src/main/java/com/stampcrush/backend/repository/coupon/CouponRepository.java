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

    // TODO: coupon_policy, coupon_design을 같이 가져오자
    @Query("select distinct cp from Coupon cp left join fetch cp.stamps join fetch cp.cafe join fetch cp.customer where cp.cafe = :cafe")
    List<Coupon> findByCafe(@Param("cafe") Cafe cafe);

    List<Coupon> findByCafeAndCustomerAndStatus(@Param("cafe") Cafe cafe, @Param("customer") Customer customer, @Param("status") CouponStatus status);

    // TODO: 즐겨찾기가 붙어있는지 등에 따라 정렬하기로 했었는데, 현재 로직에서는 정렬을 하고 있지 않아서, 아래 메서드로 대체함
    @Query("SELECT c FROM Coupon c LEFT JOIN Favorites f ON c.cafe.id = f.cafe.id WHERE c.customer = :customer AND c.status = :status ORDER BY f.isFavorites DESC, c.expiredDate ASC")
    List<Coupon> findFilteredAndSortedCoupons(@Param("customer") Customer customer, @Param("status") CouponStatus status);

    List<Coupon> findCouponsByCustomerAndStatus(Customer customer, CouponStatus couponStatus);

    Optional<Coupon> findByIdAndCustomerId(Long id, Long customerId);

    List<Coupon> findByCustomer(Customer customer);
}
