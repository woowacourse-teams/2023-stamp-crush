package com.stampcrush.backend.application.manager.coupon;

import com.stampcrush.backend.application.manager.coupon.dto.CafeCustomerFindResultDto;
import com.stampcrush.backend.application.manager.coupon.dto.CustomerAccumulatingCouponFindResultDto;
import com.stampcrush.backend.entity.cafe.Cafe;
import com.stampcrush.backend.entity.coupon.Coupon;
import com.stampcrush.backend.entity.coupon.CouponStatus;
import com.stampcrush.backend.entity.coupon.Coupons;
import com.stampcrush.backend.entity.user.Customer;
import com.stampcrush.backend.exception.CafeNotFoundException;
import com.stampcrush.backend.exception.CustomerNotFoundException;
import com.stampcrush.backend.repository.cafe.CafeCouponDesignRepository;
import com.stampcrush.backend.repository.cafe.CafePolicyRepository;
import com.stampcrush.backend.repository.cafe.CafeRepository;
import com.stampcrush.backend.repository.coupon.CouponDesignRepository;
import com.stampcrush.backend.repository.coupon.CouponPolicyRepository;
import com.stampcrush.backend.repository.coupon.CouponRepository;
import com.stampcrush.backend.repository.reward.RewardRepository;
import com.stampcrush.backend.repository.user.CustomerRepository;
import com.stampcrush.backend.repository.user.OwnerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ManagerCouponFindService {

    private final CouponRepository couponRepository;
    private final CafeRepository cafeRepository;
    private final CustomerRepository customerRepository;
    private final CafeCouponDesignRepository cafeCouponDesignRepository;
    private final CafePolicyRepository cafePolicyRepository;
    private final CouponDesignRepository couponDesignRepository;
    private final CouponPolicyRepository couponPolicyRepository;
    private final OwnerRepository ownerRepository;
    private final RewardRepository rewardRepository;

    private record CustomerCoupons(Customer customer, List<Coupon> coupons) {
    }

    public List<CafeCustomerFindResultDto> findCouponsByCafe(Long cafeId) {
        Cafe cafe = findExistingCafe(cafeId);

        List<CustomerCoupons> customerCoupons = findCouponsGroupedByCustomers(cafe);

        List<CafeCustomerFindResultDto> cafeCustomerFindResultDtos = new ArrayList<>();
        for (CustomerCoupons customerCoupon : customerCoupons) {
            Coupons coupons = new Coupons(customerCoupon.coupons);
            CustomerCouponStatistics customerCouponStatistics = coupons.calculateStatistics();
            cafeCustomerFindResultDtos.add(CafeCustomerFindResultDto.of(customerCoupon.customer, customerCouponStatistics));
        }

        return cafeCustomerFindResultDtos;
    }

    private Cafe findExistingCafe(Long cafeId) {
        return cafeRepository.findById(cafeId)
                .orElseThrow(() -> new CafeNotFoundException("존재하지 않는 카페 입니다."));
    }

    private List<CustomerCoupons> findCouponsGroupedByCustomers(Cafe cafe) {
        List<Coupon> coupons = couponRepository.findByCafe(cafe);
        Map<Customer, List<Coupon>> customerCouponMap = coupons.stream()
                .collect(groupingBy(Coupon::getCustomer));
        return customerCouponMap.keySet().stream()
                .map(iter -> new CustomerCoupons(iter, customerCouponMap.get(iter)))
                .toList();
    }

    public List<CustomerAccumulatingCouponFindResultDto> findAccumulatingCoupon(Long cafeId, Long customerId) {
        Cafe cafe = cafeRepository.findById(cafeId).orElseThrow(() -> new CustomerNotFoundException("존재하지 않는 카페 입니다."));
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new CustomerNotFoundException("존재하지 않는 고객 입니다."));

        List<Coupon> coupons = couponRepository.findByCafeAndCustomerAndStatus(cafe, customer, CouponStatus.ACCUMULATING);

        return coupons.stream()
                .map(coupon -> CustomerAccumulatingCouponFindResultDto.of(
                        coupon,
                        customer,
                        isPrevious(coupon)))
                .toList();
    }

    private boolean isPrevious(Coupon coupon) {
        return coupon.isPrevious();
    }
}
