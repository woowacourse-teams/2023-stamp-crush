package com.stampcrush.backend.application.coupon;

import com.stampcrush.backend.application.coupon.dto.CafeCustomerInfoResponseDto;
import com.stampcrush.backend.application.coupon.dto.CafeCustomersResponseDto;
import com.stampcrush.backend.entity.cafe.Cafe;
import com.stampcrush.backend.entity.coupon.Coupon;
import com.stampcrush.backend.entity.user.Customer;
import com.stampcrush.backend.repository.cafe.CafeRepository;
import com.stampcrush.backend.repository.coupon.CouponRepository;
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

    public CafeCustomersResponseDto findCouponsByCafe(Long cafeId) {
        Cafe cafe = cafeRepository.findById(cafeId).orElseThrow(() -> new NoSuchElementException("존재하지 않는 카페 입니다."));

        Map<Customer, List<Coupon>> couponsByCustomer = getCouponsByCustomer(cafe);
        List<CafeCustomerInfoResponseDto> customers = new ArrayList<>();
        for (Customer customer : couponsByCustomer.keySet()) {
            List<Coupon> coupons = couponsByCustomer.get(customer);
            int stampCount = 0;
            int rewardCount = 0;
            int visitCount = 0;
            LocalDateTime firstVisitDate = LocalDateTime.MAX;
            for (Coupon coupon : coupons) {
                if (coupon.isUsing()) {
                    stampCount = coupon.getStampCount();
                }
                if (coupon.isRewarded()) {
                    rewardCount++;
                }
                visitCount += coupon.calculateVisitCount();
                firstVisitDate = coupon.compareVisitTime(firstVisitDate);
            }
            addCustomerInfo(customers, customer, stampCount, rewardCount, visitCount, firstVisitDate);
        }
        return new CafeCustomersResponseDto(customers);
    }

    private Map<Customer, List<Coupon>> getCouponsByCustomer(Cafe cafe) {
        List<Coupon> coupons = couponRepository.findByCafe(cafe);
        return coupons.stream()
                .collect(Collectors.groupingBy(Coupon::getCustomer));
    }

    private static void addCustomerInfo(List<CafeCustomerInfoResponseDto> customers, Customer customer, int stampCount, int rewardCount, int visitCount, LocalDateTime firstVisitDate) {
        customers.add(new CafeCustomerInfoResponseDto(
                customer.getId(),
                customer.getNickname(),
                stampCount, rewardCount,
                visitCount,
                firstVisitDate,
                customer.isRegistered()
        ));
    }
}
