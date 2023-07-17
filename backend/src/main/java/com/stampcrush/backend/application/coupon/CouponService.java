package com.stampcrush.backend.application.coupon;

import com.stampcrush.backend.application.coupon.dto.CafeCustomerInfoResultDto;
import com.stampcrush.backend.application.coupon.dto.CafeCustomersResultDto;
import com.stampcrush.backend.application.coupon.dto.CustomerUsingCouponResultDto;
import com.stampcrush.backend.entity.cafe.Cafe;
import com.stampcrush.backend.entity.coupon.Coupon;
import com.stampcrush.backend.entity.coupon.CouponStatus;
import com.stampcrush.backend.entity.user.Customer;
import com.stampcrush.backend.repository.cafe.CafeRepository;
import com.stampcrush.backend.repository.coupon.CouponRepository;
import com.stampcrush.backend.repository.user.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
@Transactional
@Service
public class CouponService {

    private final CouponRepository couponRepository;
    private final CafeRepository cafeRepository;
    private final CustomerRepository customerRepository;

    public CafeCustomersResultDto findCouponsByCafe(Long cafeId) {
        Cafe cafe = cafeRepository.findById(cafeId)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 카페 입니다."));

        Map<Customer, List<Coupon>> couponsByCustomer = mapCouponsByCustomer(cafe);
        List<CafeCustomerInfoResultDto> customers = new ArrayList<>();
        for (Customer customer : couponsByCustomer.keySet()) {
            List<Coupon> coupons = couponsByCustomer.get(customer);

            CustomerInfo customerInfo = statisticsCustomerByCoupons(coupons);
            addCustomerInfo(customers, customer, customerInfo.stampCount(), customerInfo.rewardCount(), customerInfo.visitCount(), customerInfo.firstVisitDate());
        }
        return new CafeCustomersResultDto(customers);
    }

    private CustomerInfo statisticsCustomerByCoupons(List<Coupon> coupons) {
        int stampCount = 0;
        int rewardCount = 0;
        int visitCount = 0;
        LocalDateTime firstVisitDate = LocalDateTime.MAX;
        for (Coupon coupon : coupons) {
            stampCount = calculateCurrentStampWhenUsingCoupon(stampCount, coupon);
            rewardCount += addRewardCouponCount(coupon);
            visitCount += coupon.calculateVisitCount();
            firstVisitDate = coupon.compareCreatedAtReturnFaster(firstVisitDate);
        }
        return new CustomerInfo(stampCount, rewardCount, visitCount, firstVisitDate);
    }

    private record CustomerInfo(int stampCount, int rewardCount, int visitCount, LocalDateTime firstVisitDate) {
    }

    private Map<Customer, List<Coupon>> mapCouponsByCustomer(Cafe cafe) {
        List<Coupon> coupons = couponRepository.findByCafe(cafe);
        return coupons.stream()
                .collect(Collectors.groupingBy(Coupon::getCustomer));
    }

    private int calculateCurrentStampWhenUsingCoupon(int stampCount, Coupon coupon) {
        if (coupon.isUsing()) {
            stampCount = coupon.getStampCount();
        }
        return stampCount;
    }

    private int addRewardCouponCount(Coupon coupon) {
        if (coupon.isRewarded()) {
            return 1;
        }
        return 0;
    }

    private void addCustomerInfo(List<CafeCustomerInfoResultDto> customers, Customer customer, int stampCount, int rewardCount, int visitCount, LocalDateTime firstVisitDate) {
        customers.add(new CafeCustomerInfoResultDto(
                customer.getId(),
                customer.getNickname(),
                stampCount,
                rewardCount,
                visitCount,
                firstVisitDate,
                customer.isRegistered()
        ));
    }

    public List<CustomerUsingCouponResultDto> findAccumulatingCoupon(Long cafeId, Long customerId) {
        Cafe cafe = cafeRepository.findById(cafeId).orElseThrow(() -> new NoSuchElementException("존재하지 않는 카페 입니다."));
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new NoSuchElementException("존재하지 않는 고객 입니다."));

        List<Coupon> coupons = couponRepository.findByCafeAndCustomerAndStatus(cafe, customer, CouponStatus.USING);
        return coupons.stream()
                .map(coupon -> CustomerUsingCouponResultDto.of(coupon, customer, false))
                .collect(toList());
    }
}
