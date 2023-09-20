package com.stampcrush.backend.application.visitor.coupon;

import com.stampcrush.backend.application.visitor.coupon.dto.CustomerCouponFindResultDto;
import com.stampcrush.backend.application.visitor.favorites.VisitorFavoritesFindService;
import com.stampcrush.backend.entity.cafe.Cafe;
import com.stampcrush.backend.entity.coupon.Coupon;
import com.stampcrush.backend.entity.coupon.CouponStampCoordinate;
import com.stampcrush.backend.entity.coupon.CouponStatus;
import com.stampcrush.backend.entity.user.Customer;
import com.stampcrush.backend.exception.CustomerNotFoundException;
import com.stampcrush.backend.repository.coupon.CouponRepository;
import com.stampcrush.backend.repository.coupon.CouponStampCoordinateRepository;
import com.stampcrush.backend.repository.user.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class VisitorCouponFindService {

    private final VisitorFavoritesFindService visitorFavoritesFindService;
    private final CustomerRepository customerRepository;
    private final CouponRepository couponRepository;
    private final CouponStampCoordinateRepository couponStampCoordinateRepository;

    public List<CustomerCouponFindResultDto> findOneCouponForOneCafe(Long customerId) {
        // TODO: customer를 찾아서 쿼리하지 않고, 곧바로 cusotmerId를 쿼리에 사용해도 될 것 같음.
        Customer customer = findExistingCustomer(customerId);
        List<Coupon> coupons = couponRepository.findCouponsByCustomerAndStatus(customer, CouponStatus.ACCUMULATING);

        return coupons.stream()
                .map(coupon -> formatToDto(customer, coupon))
                .toList();
    }

    private CustomerCouponFindResultDto formatToDto(Customer customer, Coupon coupon) {
        Cafe cafe = coupon.getCafe();
        Boolean isFavorites = visitorFavoritesFindService.findIsFavorites(cafe, customer);
        List<CouponStampCoordinate> coordinates = couponStampCoordinateRepository.findByCouponDesign(coupon.getCouponDesign());
        return CustomerCouponFindResultDto.of(cafe, coupon, isFavorites, coordinates);
    }

    private Customer findExistingCustomer(Long customerId) {
        Optional<Customer> findCustomer = customerRepository.findById(customerId);

        if (findCustomer.isEmpty()) {
            throw new CustomerNotFoundException("해당 id의 고객을 찾을 수 없습니다. 회원가입 해 주세요.");
        }

        return findCustomer.get();
    }
}
