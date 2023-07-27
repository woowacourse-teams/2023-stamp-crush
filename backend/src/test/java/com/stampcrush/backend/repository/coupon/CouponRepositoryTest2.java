package com.stampcrush.backend.repository.coupon;

import com.stampcrush.backend.entity.cafe.Cafe;
import com.stampcrush.backend.entity.coupon.Coupon;
import com.stampcrush.backend.entity.coupon.CouponDesign;
import com.stampcrush.backend.entity.coupon.CouponPolicy;
import com.stampcrush.backend.entity.coupon.CouponStatus;
import com.stampcrush.backend.entity.user.Owner;
import com.stampcrush.backend.entity.user.RegisterCustomer;
import com.stampcrush.backend.fixture.CouponDesignFixture;
import com.stampcrush.backend.fixture.CouponPolicyFixture;
import com.stampcrush.backend.fixture.CustomerFixture;
import com.stampcrush.backend.fixture.OwnerFixture;
import com.stampcrush.backend.repository.cafe.CafeRepository;
import com.stampcrush.backend.repository.user.CustomerRepository;
import com.stampcrush.backend.repository.user.OwnerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class CouponRepositoryTest2 {

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private CafeRepository cafeRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CouponDesignRepository couponDesignRepository;

    @Autowired
    private CouponPolicyRepository couponPolicyRepository;

    @Test
    void 쿠폰을_고객으로_필터링하고_그중_ACCUMULATING_상태인_쿠폰만_필터링해_조회한다() {
        // given
        Cafe gitchanCafe = createCafe(OwnerFixture.GITCHAN);
        Cafe jenaCafe = createCafe(OwnerFixture.JENA);

        RegisterCustomer savedCustomer = customerRepository.save(CustomerFixture.REGISTER_CUSTOMER_GITCHAN);

        Coupon gitchanCafeCoupon = saveCoupon(gitchanCafe, savedCustomer, couponDesignRepository.save(CouponDesignFixture.COUPON_DESIGN_1), couponPolicyRepository.save(CouponPolicyFixture.COUPON_POLICY_1));
        Coupon jenaCafeCoupon = saveCoupon(jenaCafe, savedCustomer, couponDesignRepository.save(CouponDesignFixture.COUPON_DESIGN_2), couponPolicyRepository.save(CouponPolicyFixture.COUPON_POLICY_2));

        // when
        List<Coupon> findCoupon = couponRepository.findByCustomerAndStatus(savedCustomer, CouponStatus.ACCUMULATING);

        // then
        assertThat(findCoupon).containsExactlyInAnyOrder(gitchanCafeCoupon, jenaCafeCoupon);
    }

    @Test
    void 쿠폰을_고객으로_필터링하고_그중_ACCUMULATING_상태인_쿠폰만_필터링해_조회한다_2() {
        // given
        Cafe gitchanCafe = createCafe(OwnerFixture.GITCHAN);
        Cafe jenaCafe = createCafe(OwnerFixture.JENA);

        RegisterCustomer savedCustomer = customerRepository.save(CustomerFixture.REGISTER_CUSTOMER_GITCHAN);

        CouponPolicy gitchanCafeCouponPolicy = CouponPolicyFixture.COUPON_POLICY_1;

        Coupon gitchanCafeCoupon = saveCoupon(gitchanCafe, savedCustomer, couponDesignRepository.save(CouponDesignFixture.COUPON_DESIGN_1), couponPolicyRepository.save(gitchanCafeCouponPolicy));
        Coupon jenaCafeCoupon = saveCoupon(jenaCafe, savedCustomer, couponDesignRepository.save(CouponDesignFixture.COUPON_DESIGN_2), couponPolicyRepository.save(CouponPolicyFixture.COUPON_POLICY_2));
        changeCafeCouponStatusToRewarded(gitchanCafeCoupon, gitchanCafeCouponPolicy.getMaxStampCount());

        // when
        List<Coupon> findCoupon = couponRepository.findByCustomerAndStatus(savedCustomer, CouponStatus.ACCUMULATING);

        // then
        assertThat(findCoupon).containsOnly(jenaCafeCoupon);
    }

    private void changeCafeCouponStatusToRewarded(Coupon gitchanCafeCoupon, Integer maxStampCount) {
        for (int i = 0; i < maxStampCount; i++) {
            gitchanCafeCoupon.accumulate();
        }
    }

    private Cafe createCafe(Owner owner) {
        Owner savedOwner = ownerRepository.save(owner);
        return cafeRepository.save(
                new Cafe(
                        "깃짱카페",
                        "서초구",
                        "어쩌고",
                        "0101010101",
                        savedOwner
                )
        );
    }

    private Coupon saveCoupon(Cafe savedCafe, RegisterCustomer savedCustomer, CouponDesign savedCouponDesign, CouponPolicy savedCouponPolicy) {
        return couponRepository.save(
                new Coupon(
                        LocalDate.MAX,
                        savedCustomer,
                        savedCafe,
                        savedCouponDesign,
                        savedCouponPolicy
                )
        );
    }
}
