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

@RequiredArgsConstructor
@Transactional
@Service
public class CouponService {

    private final CouponRepository couponRepository;
    private final CafeRepository cafeRepository;
    private final CustomerRepository customerRepository;

    public CafeCustomersResultDto findCouponsByCafe(Long cafeId) {
        Cafe cafe = cafeRepository.findById(cafeId).orElseThrow(() -> new NoSuchElementException("존재하지 않는 카페 입니다."));

        Map<Customer, List<Coupon>> couponsByCustomer = mapCouponsByCustomer(cafe);
        List<CafeCustomerInfoResultDto> customers = new ArrayList<>();
        for (Customer customer : couponsByCustomer.keySet()) {
            List<Coupon> coupons = couponsByCustomer.get(customer);
            int stampCount = 0;
            int rewardCount = 0;
            int visitCount = 0;
            LocalDateTime firstVisitDate = LocalDateTime.MAX;
            for (Coupon coupon : coupons) {
                // USING상태의 쿠폰이라면 스탬프 개수 적용
                if (coupon.isUsing()) {
                    stampCount = coupon.getStampCount();
                }
                // REWARD상태의 쿠폰이라면 rewardCount증가
                if (coupon.isRewarded()) {
                    rewardCount++;
                }
                // Coupon에 찍힌 stamp들의 createdAt을 set에 add한 뒤, set.size()를 통해 방문횟수 계산
                visitCount += coupon.calculateVisitCount();
                // 첫 방문 일자는 coupon들의 createdAt을 계산해 가장 빠른 날짜로 갱신
                firstVisitDate = coupon.compareVisitTime(firstVisitDate);
            }
            addCustomerInfo(customers, customer, stampCount, rewardCount, visitCount, firstVisitDate);
        }
        return new CafeCustomersResultDto(customers);
    }

    private Map<Customer, List<Coupon>> mapCouponsByCustomer(Cafe cafe) {
        List<Coupon> coupons = couponRepository.findByCafe(cafe);
        return coupons.stream()
                .collect(Collectors.groupingBy(Coupon::getCustomer));
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

    public List<CustomerUsingCouponResultDto> findUsingCoupon(Long cafeId, Long customerId) {
        Cafe cafe = cafeRepository.findById(cafeId).orElseThrow(() -> new NoSuchElementException("존재하지 않는 카페 입니다."));
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new NoSuchElementException("존재하지 않는 고객 입니다."));

        List<Coupon> coupons = couponRepository.findByCafeAndCustomerAndStatus(cafe, customer, CouponStatus.USING);
        return coupons.stream()
                .map(coupon -> new CustomerUsingCouponResultDto(
                        coupon.getId(),
                        customerId,
                        customer.getNickname(),
                        coupon.getStampCount(),
                        coupon.calculateExpireDate(),
                        false
                ))
                .collect(Collectors.toList());
    }
}
