package com.stampcrush.backend.application.coupon;

import com.stampcrush.backend.application.coupon.dto.CafeCustomerFindResultDto;
import com.stampcrush.backend.application.coupon.dto.CafeCustomersFindResultDto;
import com.stampcrush.backend.application.coupon.dto.CustomerAccumulatingCouponFindResultDto;
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
import com.stampcrush.backend.repository.cafe.CafePolicyRepository;
import com.stampcrush.backend.entity.user.Owner;
import com.stampcrush.backend.repository.cafe.CafeCouponDesignRepository;
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

import static java.util.stream.Collectors.toList;

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
    public CafeCustomersFindResultDto findCouponsByCafe(Long cafeId) {
        Cafe cafe = cafeRepository.findById(cafeId)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 카페 입니다."));

        Map<Customer, List<Coupon>> couponsByCustomer = mapCouponsByCustomer(cafe);
        List<CafeCustomerFindResultDto> customers = new ArrayList<>();
        for (Customer customer : couponsByCustomer.keySet()) {
            List<Coupon> coupons = couponsByCustomer.get(customer);

            CustomerInfo customerInfo = statisticsCustomerByCoupons(coupons);
            addCustomerInfo(customers, customer, customerInfo.stampCount(), customerInfo.rewardCount(), customerInfo.visitCount(), customerInfo.firstVisitDate());
        }
        return new CafeCustomersFindResultDto(customers);
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
            firstVisitDate = coupon.compareCreatedAtAndReturnEarlier(firstVisitDate);
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

    private void addCustomerInfo(List<CafeCustomerFindResultDto> customers, Customer customer, int stampCount, int rewardCount, int visitCount, LocalDateTime firstVisitDate) {
        customers.add(new CafeCustomerFindResultDto(
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
    public List<CustomerAccumulatingCouponFindResultDto> findAccumulatingCoupon(Long cafeId, Long customerId) {
        Cafe cafe = cafeRepository.findById(cafeId).orElseThrow(() -> new NoSuchElementException("존재하지 않는 카페 입니다."));
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new NoSuchElementException("존재하지 않는 고객 입니다."));

        List<Coupon> coupons = couponRepository.findByCafeAndCustomerAndStatus(cafe, customer, CouponStatus.ACCUMULATING);

        return coupons.stream()
                .map(coupon -> CustomerAccumulatingCouponFindResultDto.of(
                        coupon,
                        customer,
                        isPrevious(coupon)))
                .collect(toList());
    }

    private boolean isPrevious(Coupon coupon) {
        return !cafePolicyRepository
                .findByCafeAndCreatedAtGreaterThan(coupon.getCafe(), coupon.getCreatedAt())
                .isEmpty();
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
        List<Coupon> existCoupons = couponRepository.findByCafeAndCustomerAndStatus(cafe, customer, CouponStatus.ACCUMULATING);
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
