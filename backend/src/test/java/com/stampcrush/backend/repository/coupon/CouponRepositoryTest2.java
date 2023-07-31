package com.stampcrush.backend.repository.coupon;

import com.stampcrush.backend.entity.cafe.Cafe;
import com.stampcrush.backend.entity.coupon.*;
import com.stampcrush.backend.entity.user.Owner;
import com.stampcrush.backend.entity.user.RegisterCustomer;
import com.stampcrush.backend.fixture.CouponDesignFixture;
import com.stampcrush.backend.fixture.CouponPolicyFixture;
import com.stampcrush.backend.fixture.CustomerFixture;
import com.stampcrush.backend.fixture.OwnerFixture;
import com.stampcrush.backend.repository.cafe.CafeRepository;
import com.stampcrush.backend.repository.user.CustomerRepository;
import com.stampcrush.backend.repository.user.OwnerRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest
class CouponRepositoryTest2 {

    @Autowired
    private EntityManager em;

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

    @Autowired
    private CouponStampCoordinateRepository couponStampCoordinateRepository;

    @Test
    @Disabled
    void 쿠폰_디자인에_읽기_전용인_좌표를_조회한다() {
        // given, when
        Cafe gitchanCafe = createCafe(OwnerFixture.GITCHAN);

        CouponDesign couponDesign = CouponDesignFixture.COUPON_DESIGN_1;
        CouponStampCoordinate coordinates = new CouponStampCoordinate(1, 1, 1, couponDesign);
        addCouponStampCoordinate(List.of(coordinates), couponDesign);

        RegisterCustomer savedCustomer = customerRepository.save(CustomerFixture.REGISTER_CUSTOMER_GITCHAN);
        Coupon gitchanCafeCoupon = saveCoupon(gitchanCafe, savedCustomer, couponDesignRepository.save(couponDesign), couponPolicyRepository.save(CouponPolicyFixture.COUPON_POLICY_1));

        em.flush();
        em.clear();

        Coupon findCoupon = couponRepository.findById(gitchanCafeCoupon.getId()).get();
        List<CouponStampCoordinate> couponStampCoordinates = findCoupon.getCouponDesign().getCouponStampCoordinates();

        // then
        assertAll(
                () -> assertThat(couponStampCoordinates).isNotEmpty(),
                () -> assertThat(couponStampCoordinates).containsExactlyInAnyOrder(coordinates)
        );
    }

    private void addCouponStampCoordinate(List<CouponStampCoordinate> coordinates, CouponDesign couponDesign) {
        for (CouponStampCoordinate coordinate : coordinates) {
            couponDesign.addCouponStampCoordinate(couponStampCoordinateRepository.save(coordinate));
        }
    }

    @Test
    void 쿠폰이_참조하는_카페를_찾을_수_있다() {
        // given, when
        Cafe gitchanCafe = createCafe(OwnerFixture.GITCHAN);
        RegisterCustomer savedCustomer = customerRepository.save(CustomerFixture.REGISTER_CUSTOMER_GITCHAN);
        Coupon gitchanCafeCoupon = saveCoupon(gitchanCafe, savedCustomer, couponDesignRepository.save(CouponDesignFixture.COUPON_DESIGN_1), couponPolicyRepository.save(CouponPolicyFixture.COUPON_POLICY_1));
        Cafe cafe = gitchanCafeCoupon.getCafe();

        // then
        assertAll(
                () -> assertThat(cafe).isNotNull(),
                () -> assertThat(cafe.getId()).isEqualTo(gitchanCafe.getId()),
                () -> assertThat(cafe.getName()).isEqualTo(gitchanCafe.getName())
        );
    }

    @Test
    void 쿠폰이_참조하는_카페정책을_찾을_수_있다() {
        // given, when
        Cafe gitchanCafe = createCafe(OwnerFixture.GITCHAN);
        RegisterCustomer savedCustomer = customerRepository.save(CustomerFixture.REGISTER_CUSTOMER_GITCHAN);
        Coupon gitchanCafeCoupon = saveCoupon(gitchanCafe, savedCustomer, couponDesignRepository.save(CouponDesignFixture.COUPON_DESIGN_1), couponPolicyRepository.save(CouponPolicyFixture.COUPON_POLICY_1));
        CouponPolicy couponPolicy = gitchanCafeCoupon.getCouponPolicy();

        // then
        assertAll(
                () -> assertThat(couponPolicy).isNotNull(),
                () -> assertThat(couponPolicy).isEqualTo(gitchanCafeCoupon.getCouponPolicy())
        );
    }

    @Test
    void 쿠폰이_참조하는_쿠폰디자인을_찾을_수_있다() {
        Cafe gitchanCafe = createCafe(OwnerFixture.GITCHAN);
        RegisterCustomer savedCustomer = customerRepository.save(CustomerFixture.REGISTER_CUSTOMER_GITCHAN);
        Coupon gitchanCafeCoupon = saveCoupon(gitchanCafe, savedCustomer, couponDesignRepository.save(CouponDesignFixture.COUPON_DESIGN_1), couponPolicyRepository.save(CouponPolicyFixture.COUPON_POLICY_1));
        CouponDesign couponDesign = gitchanCafeCoupon.getCouponDesign();

        // then
        assertAll(
                () -> assertThat(couponDesign).isNotNull(),
                () -> assertThat(couponDesign).isEqualTo(gitchanCafeCoupon.getCouponDesign())
        );
    }

    @Test
    void 쿠폰에_적립된_스탬프의_개수를_찾을_수_있다() {
        // given, when
        Cafe gitchanCafe = createCafe(OwnerFixture.GITCHAN);
        RegisterCustomer savedCustomer = customerRepository.save(CustomerFixture.REGISTER_CUSTOMER_GITCHAN);
        Coupon gitchanCafeCoupon = saveCoupon(gitchanCafe, savedCustomer, couponDesignRepository.save(CouponDesignFixture.COUPON_DESIGN_1), couponPolicyRepository.save(CouponPolicyFixture.COUPON_POLICY_1));

        int stampCount = 4;
        for (int i = 0; i < stampCount; i++) {
            gitchanCafeCoupon.accumulate();
        }

        em.flush();
        em.clear();

        Coupon findCoupon = couponRepository.findById(gitchanCafeCoupon.getId()).get();

        // then
        assertThat(findCoupon.getStampCount()).isEqualTo(stampCount);
    }

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

    private void changeCafeCouponStatusToRewarded(Coupon gitchanCafeCoupon, Integer maxStampCount) {
        for (int i = 0; i < maxStampCount; i++) {
            gitchanCafeCoupon.accumulate();
        }
    }
}
