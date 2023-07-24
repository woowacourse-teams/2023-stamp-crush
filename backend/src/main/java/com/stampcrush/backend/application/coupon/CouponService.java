package com.stampcrush.backend.application.coupon;

import com.stampcrush.backend.application.coupon.dto.CafeCustomerFindResultDto;
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
import com.stampcrush.backend.entity.user.Owner;
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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;

@RequiredArgsConstructor
@Transactional
@Service
public class CouponService {

    private static final int DEFAULT_MAX_COUNT = 10;

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

    @Transactional(readOnly = true)
    public List<CafeCustomerFindResultDto> findCouponsByCafe(Long cafeId) {
        Cafe cafe = findExistingCafe(cafeId);

        List<CustomerCoupons> customerCoupons = findCouponsGroupedByCustomers(cafe);

        List<CafeCustomerFindResultDto> cafeCustomerFindResultDtos = new ArrayList<>();
        for (CustomerCoupons customerCoupon : customerCoupons) {
            List<Coupon> coupons = customerCoupon.coupons;
            CustomerCouponStatistics customerCouponStatistics = CustomerCouponStatistics.produceFrom(coupons);
            cafeCustomerFindResultDtos.add(CafeCustomerFindResultDto.from(customerCoupon.customer, customerCouponStatistics));
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

    @Transactional(readOnly = true)
    public List<CustomerAccumulatingCouponFindResultDto> findAccumulatingCoupon(Long cafeId, Long customerId) {
        Cafe cafe = cafeRepository.findById(cafeId).orElseThrow(() -> new CustomerNotFoundException("존재하지 않는 카페 입니다."));
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new CustomerNotFoundException("존재하지 않는 고객 입니다."));

        List<Coupon> coupons = couponRepository.findByCafeAndCustomerAndStatus(cafe, customer, CouponStatus.ACCUMULATING);

        return coupons.stream()
                .map(coupon -> CustomerAccumulatingCouponFindResultDto.from(
                        coupon,
                        customer,
                        isPrevious(coupon)))
                .toList();
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
        CafePolicy cafePolicy = cafePolicyRepository.findByCafe(cafe)
                .orElseThrow(IllegalArgumentException::new);
        CafeCouponDesign cafeCouponDesign = cafeCouponDesignRepository.findByCafe(cafe)
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

        LocalDate expiredDate = LocalDate.now().plusMonths(couponPolicy.getExpiredPeriod());

        Coupon coupon = new Coupon(expiredDate, customer, cafe, couponDesign, couponPolicy);
        return couponRepository.save(coupon);
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

        CafePolicy cafePolicy = cafePolicyRepository.findByCafe(cafe)
                .orElseThrow(IllegalArgumentException::new);
        CafeCouponDesign cafeCouponDesign = cafeCouponDesignRepository.findByCafe(cafe)
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
