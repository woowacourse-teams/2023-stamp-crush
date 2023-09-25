package com.stampcrush.backend.application.manager.coupon;

import com.stampcrush.backend.application.manager.coupon.dto.CafeCustomerFindResultDto;
import com.stampcrush.backend.application.manager.coupon.dto.CustomerAccumulatingCouponFindResultDto;
import com.stampcrush.backend.entity.cafe.Cafe;
import com.stampcrush.backend.entity.coupon.Coupon;
import com.stampcrush.backend.entity.coupon.CouponStatus;
import com.stampcrush.backend.entity.coupon.Coupons;
import com.stampcrush.backend.entity.user.Customer;
import com.stampcrush.backend.entity.user.Owner;
import com.stampcrush.backend.entity.visithistory.VisitHistories;
import com.stampcrush.backend.entity.visithistory.VisitHistory;
import com.stampcrush.backend.exception.CafeNotFoundException;
import com.stampcrush.backend.exception.CustomerNotFoundException;
import com.stampcrush.backend.exception.OwnerNotFoundException;
import com.stampcrush.backend.repository.cafe.CafeRepository;
import com.stampcrush.backend.repository.coupon.CouponRepository;
import com.stampcrush.backend.repository.reward.RewardRepository;
import com.stampcrush.backend.repository.user.CustomerRepository;
import com.stampcrush.backend.repository.user.OwnerRepository;
import com.stampcrush.backend.repository.visithistory.VisitHistoryRepository;
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
    private final VisitHistoryRepository visitHistoryRepository;
    private final RewardRepository rewardRepository;
    private final OwnerRepository ownerRepository;

    public List<CafeCustomerFindResultDto> findCouponsByCafe(Long ownerId, Long cafeId) {
        Cafe cafe = findExistingCafe(cafeId);
        Owner owner = ownerRepository.findById(ownerId)
                .orElseThrow(() -> new OwnerNotFoundException("사장을 찾지 못했습니다."));
        cafe.validateOwnership(owner);

        List<Coupon> allCustomerCoupons = couponRepository.findByCafe(cafe);
        Map<Customer, List<Coupon>> customerCouponMap = mapCouponsByCustomer(allCustomerCoupons);
        List<CafeCustomerFindResultDto> cafeCustomerFindResultDtos = new ArrayList<>();
        for (Map.Entry<Customer, List<Coupon>> customerEntry : customerCouponMap.entrySet()) {
            Coupons coupons = new Coupons(customerEntry.getValue());
            Customer customer = customerEntry.getKey();

            VisitHistories visitHistories = findVisitHistories(cafe, customer);
            if (visitHistories.getVisitCount() == 0) {
                continue;
            }
            CustomerCouponStatistics customerCouponStatistics = coupons.calculateStatistics();
            int unusedRewards = (int) countUnusedRewards(cafe, customer);
            cafeCustomerFindResultDtos.add(CafeCustomerFindResultDto.of(customer, customerCouponStatistics, visitHistories, unusedRewards));
        }

        return cafeCustomerFindResultDtos;
    }

    private Map<Customer, List<Coupon>> mapCouponsByCustomer(List<Coupon> coupons) {
        return coupons.stream()
                .collect(groupingBy(Coupon::getCustomer));
    }

    private Cafe findExistingCafe(Long cafeId) {
        return cafeRepository.findById(cafeId)
                .orElseThrow(() -> new CafeNotFoundException("존재하지 않는 카페 입니다."));
    }

    private VisitHistories findVisitHistories(Cafe cafe, Customer customer) {
        List<VisitHistory> visitHistories = visitHistoryRepository.findByCafeAndCustomer(cafe, customer);

        return new VisitHistories(visitHistories);
    }

    public List<CustomerAccumulatingCouponFindResultDto> findAccumulatingCoupon(Long ownerId, Long cafeId, Long customerId) {
        Cafe cafe = cafeRepository.findById(cafeId).orElseThrow(() -> new CustomerNotFoundException("존재하지 않는 카페 입니다."));
        Owner owner = ownerRepository.findById(ownerId)
                .orElseThrow(() -> new OwnerNotFoundException("사장을 찾지 못했습니다."));
        cafe.validateOwnership(owner);

        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new CustomerNotFoundException("존재하지 않는 고객 입니다."));

        List<Coupon> coupons = couponRepository.findByCafeAndCustomerAndStatus(cafe, customer, CouponStatus.ACCUMULATING);

        return coupons.stream()
                .map(coupon -> CustomerAccumulatingCouponFindResultDto.of(
                                coupon,
                                customer,
                                coupon.isPrevious()
                        )
                )
                .toList();
    }

    private long countUnusedRewards(Cafe cafe, Customer customer) {
        return rewardRepository.countByCafeAndCustomerAndUsed(cafe, customer, Boolean.FALSE);
    }
}
