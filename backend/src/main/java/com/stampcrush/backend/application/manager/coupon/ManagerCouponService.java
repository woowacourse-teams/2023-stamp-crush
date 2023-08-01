package com.stampcrush.backend.application.manager.coupon;

import com.stampcrush.backend.application.manager.coupon.dto.CafeCustomerFindResultDto;
import com.stampcrush.backend.application.manager.coupon.dto.CustomerAccumulatingCouponFindResultDto;
import com.stampcrush.backend.application.manager.coupon.dto.StampCreateDto;
import com.stampcrush.backend.entity.cafe.Cafe;
import com.stampcrush.backend.entity.cafe.CafeCouponDesign;
import com.stampcrush.backend.entity.cafe.CafePolicy;
import com.stampcrush.backend.entity.coupon.*;
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
public class ManagerCouponService {

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
            Coupons coupons = new Coupons(customerCoupon.coupons);
            CustomerCouponStatistics customerCouponStatistics = coupons.calculateStatistics();
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
                .map(coupon -> CustomerAccumulatingCouponFindResultDto.of(
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
        CafePolicy cafePolicy = findCafePolicy(cafe);
        CafeCouponDesign cafeCouponDesign = findCafeCouponDesign(cafe);
        List<Coupon> existCoupons = couponRepository.findByCafeAndCustomerAndStatus(cafe, customer, CouponStatus.ACCUMULATING);
        if (!existCoupons.isEmpty()) {
            for (Coupon coupon : existCoupons) {
                coupon.expire();
            }
        }

        Coupon coupon = issueCoupon(customer, cafe, cafePolicy, cafeCouponDesign);
        couponRepository.save(coupon);
        return coupon.getId();
    }

    private Coupon issueCoupon(Customer customer, Cafe cafe, CafePolicy cafePolicy, CafeCouponDesign cafeCouponDesign) {
        CouponDesign couponDesign = cafeCouponDesign.copy();
        couponDesignRepository.save(couponDesign);
        CouponPolicy couponPolicy = cafePolicy.copy();
        couponPolicyRepository.save(couponPolicy);

        LocalDate expiredDate = LocalDate.now().plusMonths(couponPolicy.getExpiredPeriod());

        return new Coupon(expiredDate, customer, cafe, couponDesign, couponPolicy);
    }

    public void createStamp(StampCreateDto stampCreateDto) {
        Owner owner = findOwner(stampCreateDto);
        Customer customer = findCustomer(stampCreateDto);
        Cafe cafe = findCafe(owner);
        CafePolicy cafePolicy = findCafePolicy(cafe);
        CafeCouponDesign cafeCouponDesign = findCafeCouponDesign(cafe);
        Coupon coupon = findCoupon(stampCreateDto, customer, cafe);

        int earningStampCount = stampCreateDto.getEarningStampCount();
        if (coupon.isLessThanMaxStampAfterAccumulateStamp(earningStampCount)) {
            coupon.accumulate(earningStampCount);
            return;
        }
        if (coupon.isSameMaxStampAfterAccumulateStamp(earningStampCount)) {
            accumulateMaxStampAndMakeReward(customer, cafe, coupon, earningStampCount);
            return;
        }
        int restStampCountForReward = coupon.calculateRestStampCountForReward();
        accumulateMaxStampAndMakeReward(customer, cafe, coupon, restStampCountForReward);

        int restStamp = earningStampCount - restStampCountForReward;
        makeRewardCoupons(customer, cafe, cafePolicy, cafeCouponDesign, coupon, restStamp);
        issueAccumulatingCoupon(customer, cafe, cafePolicy, cafeCouponDesign, restStamp);
    }

    private CafeCouponDesign findCafeCouponDesign(Cafe cafe) {
        return cafeCouponDesignRepository.findByCafe(cafe)
                .orElseThrow(IllegalArgumentException::new);
    }

    private CafePolicy findCafePolicy(Cafe cafe) {
        return cafePolicyRepository.findByCafe(cafe)
                .orElseThrow(IllegalArgumentException::new);
    }

    private Customer findCustomer(StampCreateDto stampCreateDto) {
        return customerRepository.findById(stampCreateDto.getCustomerId())
                .orElseThrow(IllegalArgumentException::new);
    }

    private Owner findOwner(StampCreateDto stampCreateDto) {
        return ownerRepository.findById(stampCreateDto.getOwnerId())
                .orElseThrow(IllegalArgumentException::new);
    }

    private Cafe findCafe(Owner owner) {
        List<Cafe> cafes = cafeRepository.findAllByOwner(owner);
        if (cafes.isEmpty()) {
            throw new IllegalArgumentException();
        }
        return cafes.stream()
                .findAny()
                .get();
    }

    private Coupon findCoupon(StampCreateDto stampCreateDto, Customer customer, Cafe cafe) {
        Coupon coupon = couponRepository.findById(stampCreateDto.getCouponId())
                .orElseThrow(IllegalArgumentException::new);
        if (coupon.isNotAccessible(customer, cafe)) {
            throw new IllegalArgumentException();
        }
        return coupon;
    }

    private void accumulateMaxStampAndMakeReward(Customer customer, Cafe cafe, Coupon coupon, int earningStampCount) {
        coupon.accumulate(earningStampCount);
        rewardRepository.save(new Reward(coupon.getRewardName(), customer, cafe));
    }

    private void issueAccumulatingCoupon(Customer customer, Cafe cafe, CafePolicy cafePolicy, CafeCouponDesign cafeCouponDesign, int earningStampCount) {
        Coupon accumulatingCoupon = issueCoupon(customer, cafe, cafePolicy, cafeCouponDesign);
        couponRepository.save(accumulatingCoupon);
        int accumulatingStampCount = earningStampCount % cafePolicy.getMaxStampCount();
        accumulatingCoupon.accumulate(accumulatingStampCount);
    }

    private void makeRewardCoupons(Customer customer, Cafe cafe, CafePolicy cafePolicy, CafeCouponDesign cafeCouponDesign, Coupon coupon, int restStamp) {
        int rewardCouponCount = cafePolicy.calculateRewardCouponCount(restStamp);
        for (int i = 0; i < rewardCouponCount; i++) {
            Coupon rewardCoupon = issueCoupon(customer, cafe, cafePolicy, cafeCouponDesign);
            rewardCoupon.accumulateMaxStamp();
            couponRepository.save(rewardCoupon);
            rewardRepository.save(new Reward(coupon.getRewardName(), customer, cafe));
        }
    }
}
