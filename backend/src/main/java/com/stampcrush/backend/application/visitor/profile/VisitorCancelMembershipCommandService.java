package com.stampcrush.backend.application.visitor.profile;

import com.stampcrush.backend.entity.coupon.Coupon;
import com.stampcrush.backend.entity.favorites.Favorites;
import com.stampcrush.backend.entity.reward.Reward;
import com.stampcrush.backend.entity.user.Customer;
import com.stampcrush.backend.entity.visithistory.VisitHistory;
import com.stampcrush.backend.exception.CustomerNotFoundException;
import com.stampcrush.backend.repository.coupon.CouponRepository;
import com.stampcrush.backend.repository.favorites.FavoritesRepository;
import com.stampcrush.backend.repository.reward.RewardRepository;
import com.stampcrush.backend.repository.user.CustomerRepository;
import com.stampcrush.backend.repository.visithistory.VisitHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional
@Service
public class VisitorCancelMembershipCommandService {

    private final CustomerRepository customerRepository;
    private final RewardRepository rewardRepository;
    private final FavoritesRepository favoritesRepository;
    private final VisitHistoryRepository visitHistoryRepository;
    private final CouponRepository couponRepository;

    public void cancelMembership(Long customerId) {
        Customer customer = findExistingCustomer(customerId);

        List<Coupon> coupons = couponRepository.findByCustomer(customer);
        couponRepository.deleteAll(coupons);

        List<Reward> rewards = rewardRepository.findByCustomer(customer);
        rewardRepository.deleteAll(rewards);

        List<Favorites> favorites = favoritesRepository.findByCustomer(customer);
        favoritesRepository.deleteAll(favorites);

        List<VisitHistory> visitHistories = visitHistoryRepository.findByCustomer(customer);
        visitHistoryRepository.deleteAll(visitHistories);

        customerRepository.delete(customer);
    }

    private Customer findExistingCustomer(Long customerId) {
        Optional<Customer> customer = customerRepository.findById(customerId);
        if (customer.isEmpty()) {
            throw new CustomerNotFoundException("해당 id: " + customerId + "에 해당하는 고객이 존재하지 않습니다.");
        }
        return customer.get();
    }
}
