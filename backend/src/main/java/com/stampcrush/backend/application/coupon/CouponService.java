package com.stampcrush.backend.application.coupon;

import com.stampcrush.backend.application.coupon.dto.CafeCustomerInfoResultDto;
import com.stampcrush.backend.application.coupon.dto.CafeCustomersResultDto;
import com.stampcrush.backend.application.coupon.dto.CustomerUsingCouponResultDto;
import com.stampcrush.backend.application.coupon.dto.StampCreateDto;
import com.stampcrush.backend.entity.cafe.Cafe;
import com.stampcrush.backend.entity.cafe.CafeCouponDesign;
import com.stampcrush.backend.entity.cafe.CafePolicy;
import com.stampcrush.backend.entity.coupon.Coupon;
import com.stampcrush.backend.entity.coupon.CouponDesign;
import com.stampcrush.backend.entity.coupon.CouponPolicy;
import com.stampcrush.backend.entity.coupon.CouponStatus;
import com.stampcrush.backend.entity.reward.Reward;
import com.stampcrush.backend.entity.user.Customer;
import com.stampcrush.backend.entity.user.Owner;
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

import java.time.LocalDate;
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
    private final CafeCouponDesignRepository cafeCouponDesignRepository;
    private final CafePolicyRepository cafePolicyRepository;
    private final CouponDesignRepository couponDesignRepository;
    private final CouponPolicyRepository couponPolicyRepository;
    private final OwnerRepository ownerRepository;
    private final RewardRepository rewardRepository;

    @Transactional(readOnly = true)
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

    @Transactional(readOnly = true)
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

    public Long createCoupon(Long cafeId, Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(IllegalArgumentException::new);
        Cafe cafe = cafeRepository.findById(cafeId)
                .orElseThrow(IllegalArgumentException::new);
        CafePolicy cafePolicy = cafePolicyRepository.findByCafeAndDeletedIsFalse(cafe)
                .orElseThrow(IllegalArgumentException::new);
        CafeCouponDesign cafeCouponDesign = cafeCouponDesignRepository.findByCafeAndDeletedIsFalse(cafe)
                .orElseThrow(IllegalArgumentException::new);
        List<Coupon> existCoupons = couponRepository.findByCafeAndCustomerAndStatus(cafe, customer, CouponStatus.USING);
        if (!existCoupons.isEmpty()) {
            for (Coupon coupon : existCoupons) {
                coupon.expire();
            }
        }

        Coupon coupon = issueCoupon(customer, cafe, cafePolicy, cafeCouponDesign);
        return coupon.getId();
    }

    private Coupon issueCoupon(Customer customer, Cafe cafe, CafePolicy cafePolicy, CafeCouponDesign cafeCouponDesign) {
        CouponDesign couponDesign = cafeCouponDesign.copy();
        couponDesignRepository.save(couponDesign);
        CouponPolicy couponPolicy = cafePolicy.copy();
        couponPolicyRepository.save(couponPolicy);

        Coupon coupon = new Coupon(LocalDate.now().plusMonths(couponPolicy.getExpiredPeriod()), customer, cafe, couponDesign, couponPolicy);
        couponRepository.save(coupon);
        return coupon;
    }

    public void createStamp(StampCreateDto stampCreateDto) {
        Owner owner = ownerRepository.findById(stampCreateDto.getOwnerId())
                .orElseThrow(IllegalArgumentException::new);
        Customer customer = customerRepository.findById(stampCreateDto.getCustomerId())
                .orElseThrow(IllegalArgumentException::new);

        List<Cafe> cafes = cafeRepository.findAllByOwner(owner);
        if (cafes.isEmpty()) {
            throw new IllegalArgumentException();
        }
        Cafe cafe = cafes.stream()
                .findAny()
                .get();

        CafePolicy cafePolicy = cafePolicyRepository.findByCafeAndDeletedIsFalse(cafe)
                .orElseThrow(IllegalArgumentException::new);
        CafeCouponDesign cafeCouponDesign = cafeCouponDesignRepository.findByCafeAndDeletedIsFalse(cafe)
                .orElseThrow(IllegalArgumentException::new);

        Coupon coupon = couponRepository.findById(stampCreateDto.getCouponId())
                .orElseThrow(IllegalArgumentException::new);
        if (coupon.isNotAccessible(customer, cafe)) {
            throw new IllegalArgumentException();
        }
        Integer earningStampCount = stampCreateDto.getEarningStampCount();
        while (earningStampCount-- > 0) {
            coupon.accumulate();
            if (coupon.isRewarded()) {
                rewardRepository.save(new Reward(coupon.getRewardName(), customer, cafe));
            }
            if (earningStampCount > 0 && coupon.isRewarded()) {
                coupon = issueCoupon(customer, cafe, cafePolicy, cafeCouponDesign);
            }
        }
    }
}
