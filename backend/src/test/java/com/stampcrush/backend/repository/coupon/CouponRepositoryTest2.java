package com.stampcrush.backend.repository.coupon;

import com.stampcrush.backend.common.KorNamingConverter;
import com.stampcrush.backend.entity.cafe.Cafe;
import com.stampcrush.backend.entity.cafe.CafeCouponDesign;
import com.stampcrush.backend.entity.cafe.CafePolicy;
import com.stampcrush.backend.entity.cafe.CafeStampCoordinate;
import com.stampcrush.backend.entity.coupon.Coupon;
import com.stampcrush.backend.entity.coupon.CouponStatus;
import com.stampcrush.backend.entity.user.Customer;
import com.stampcrush.backend.entity.user.CustomerType;
import com.stampcrush.backend.entity.user.Owner;
import com.stampcrush.backend.fixture.CafeCouponDesignFixture;
import com.stampcrush.backend.fixture.CafePolicyFixture;
import com.stampcrush.backend.fixture.CustomerFixture;
import com.stampcrush.backend.fixture.OwnerFixture;
import com.stampcrush.backend.repository.cafe.CafeCouponDesignRepository;
import com.stampcrush.backend.repository.cafe.CafePolicyRepository;
import com.stampcrush.backend.repository.cafe.CafeRepository;
import com.stampcrush.backend.repository.cafe.CafeStampCoordinateRepository;
import com.stampcrush.backend.repository.user.CustomerRepository;
import com.stampcrush.backend.repository.user.OwnerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@KorNamingConverter
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
    private CafeCouponDesignRepository cafeCouponDesignRepository;

    @Autowired
    private CafePolicyRepository cafePolicyRepository;

    @Autowired
    private CafeStampCoordinateRepository cafeStampCoordinateRepository;

    @Test
    void 쿠폰_디자인에_읽기_전용인_좌표를_조회한다() {
        // given, when
        Cafe gitchanCafe = createCafe(OwnerFixture.GITCHAN);

        CafeCouponDesign cafeCouponDesign = cafeCouponDesignRepository.save(CafeCouponDesignFixture.COUPON_DESIGN_1);
        CafeStampCoordinate coordinates = new CafeStampCoordinate(1, 1, 1, cafeCouponDesign);
        addCouponStampCoordinate(List.of(coordinates), cafeCouponDesign);

        Customer savedCustomer = customerRepository.save(CustomerFixture.REGISTER_CUSTOMER_GITCHAN);
        Coupon gitchanCafeCoupon = saveCoupon(gitchanCafe, savedCustomer, cafeCouponDesignRepository.save(cafeCouponDesign), cafePolicyRepository.save(CafePolicyFixture.COUPON_POLICY_1));

        Coupon findCoupon = couponRepository.findById(gitchanCafeCoupon.getId()).get();
        List<CafeStampCoordinate> couponStampCoordinates = findCoupon.getCafeCouponDesign().getCafeStampCoordinates();

        // then
        assertAll(
                () -> assertThat(couponStampCoordinates).isNotEmpty(),
                () -> assertThat(couponStampCoordinates).containsExactlyInAnyOrder(coordinates)
        );
    }

    private void addCouponStampCoordinate(List<CafeStampCoordinate> coordinates, CafeCouponDesign cafeCouponDesign) {
        for (CafeStampCoordinate coordinate : coordinates) {
            cafeCouponDesign.addCouponStampCoordinate(cafeStampCoordinateRepository.save(coordinate));
        }
    }

    @Test
    void 쿠폰이_참조하는_카페를_찾을_수_있다() {
        // given, when
        Cafe gitchanCafe = createCafe(OwnerFixture.GITCHAN);
        Customer savedCustomer = customerRepository.save(CustomerFixture.REGISTER_CUSTOMER_GITCHAN);
        Coupon gitchanCafeCoupon = saveCoupon(gitchanCafe, savedCustomer, cafeCouponDesignRepository.save(CafeCouponDesignFixture.COUPON_DESIGN_1), cafePolicyRepository.save(CafePolicyFixture.COUPON_POLICY_1));
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
        Customer savedCustomer = customerRepository.save(CustomerFixture.REGISTER_CUSTOMER_GITCHAN);
        Coupon gitchanCafeCoupon = saveCoupon(gitchanCafe, savedCustomer, cafeCouponDesignRepository.save(CafeCouponDesignFixture.COUPON_DESIGN_1), cafePolicyRepository.save(CafePolicyFixture.COUPON_POLICY_1));
        CafePolicy couponPolicy = gitchanCafeCoupon.getCafePolicy();

        // then
        assertAll(
                () -> assertThat(couponPolicy).isNotNull(),
                () -> assertThat(couponPolicy).isEqualTo(gitchanCafeCoupon.getCafePolicy())
        );
    }

    @Test
    void 쿠폰이_참조하는_쿠폰디자인을_찾을_수_있다() {
        Cafe gitchanCafe = createCafe(OwnerFixture.GITCHAN);
        Customer savedCustomer = customerRepository.save(CustomerFixture.REGISTER_CUSTOMER_GITCHAN);
        Coupon gitchanCafeCoupon = saveCoupon(gitchanCafe, savedCustomer, cafeCouponDesignRepository.save(CafeCouponDesignFixture.COUPON_DESIGN_1), cafePolicyRepository.save(CafePolicyFixture.COUPON_POLICY_1));
        CafeCouponDesign couponDesign = gitchanCafeCoupon.getCafeCouponDesign();

        // then
        assertAll(
                () -> assertThat(couponDesign).isNotNull(),
                () -> assertThat(couponDesign).isEqualTo(gitchanCafeCoupon.getCafeCouponDesign())
        );
    }

    @Test
    void 쿠폰에_적립된_스탬프의_개수를_찾을_수_있다() {
        // given, when
        Cafe gitchanCafe = createCafe(OwnerFixture.GITCHAN);
        Customer savedCustomer = customerRepository.save(CustomerFixture.REGISTER_CUSTOMER_GITCHAN);
        Coupon gitchanCafeCoupon = saveCoupon(gitchanCafe, savedCustomer, cafeCouponDesignRepository.save(CafeCouponDesignFixture.COUPON_DESIGN_1), cafePolicyRepository.save(CafePolicyFixture.COUPON_POLICY_1));

        int stampCount = 4;
        for (int i = 0; i < stampCount; i++) {
            gitchanCafeCoupon.accumulate(1);
        }

        Coupon findCoupon = couponRepository.findById(gitchanCafeCoupon.getId()).get();

        // then
        assertThat(findCoupon.getStampCount()).isEqualTo(stampCount);
    }

    @Test
    void 쿠폰을_고객으로_필터링하고_그중_ACCUMULATING_상태인_쿠폰만_필터링해_조회한다() {
        // given
        Cafe gitchanCafe = createCafe(OwnerFixture.GITCHAN);
        Cafe jenaCafe = createCafe(OwnerFixture.JENA);

        CafePolicy gitchanCafePolicy = cafePolicyRepository.save(new CafePolicy(10, "Americano", 6, gitchanCafe));
        CafePolicy jenaCafePolicy = cafePolicyRepository.save(new CafePolicy(10, "IceVanillaLatte", 12, jenaCafe));

        CafeCouponDesign gitchanCafeCouponDesign = cafeCouponDesignRepository.save(new CafeCouponDesign("front", "back", "stamp", gitchanCafe));
        CafeCouponDesign jenaCafeCouponDesign = cafeCouponDesignRepository.save(new CafeCouponDesign("front", "back", "stamp", jenaCafe));

        Customer savedCustomer = customerRepository.save(CustomerFixture.REGISTER_CUSTOMER_GITCHAN);

        Coupon gitchanCafeCoupon = saveCoupon(gitchanCafe, savedCustomer, gitchanCafeCouponDesign, gitchanCafePolicy);
        Coupon jenaCafeCoupon = saveCoupon(jenaCafe, savedCustomer, jenaCafeCouponDesign, jenaCafePolicy);

        // when
        List<Coupon> findCoupon = couponRepository.findCouponsByCustomerAndStatus(savedCustomer, CouponStatus.ACCUMULATING);

        // then
        assertThat(findCoupon).containsExactlyInAnyOrder(gitchanCafeCoupon, jenaCafeCoupon);
    }

    @Test
    void 쿠폰을_고객으로_필터링하고_그중_ACCUMULATING_상태인_쿠폰만_필터링해_조회한다_2() {
        // given
        Cafe gitchanCafe = createCafe(OwnerFixture.GITCHAN);
        Cafe jenaCafe = createCafe(OwnerFixture.JENA);

        CafePolicy gitchanCafePolicy = cafePolicyRepository.save(new CafePolicy(10, "Americano", 6, gitchanCafe));
        CafePolicy jenaCafePolicy = cafePolicyRepository.save(new CafePolicy(10, "IceVanillaLatte", 12, jenaCafe));

        CafeCouponDesign gitchanCafeCouponDesign = cafeCouponDesignRepository.save(new CafeCouponDesign("front", "back", "stamp", gitchanCafe));
        CafeCouponDesign jenaCafeCouponDesign = cafeCouponDesignRepository.save(new CafeCouponDesign("front", "back", "stamp", jenaCafe));

        Customer savedCustomer = customerRepository.save(CustomerFixture.REGISTER_CUSTOMER_GITCHAN);
        Coupon gitchanCafeCoupon = saveCoupon(gitchanCafe, savedCustomer, gitchanCafeCouponDesign, gitchanCafePolicy);
        Coupon jenaCafeCoupon = saveCoupon(jenaCafe, savedCustomer, jenaCafeCouponDesign, jenaCafePolicy);
        changeCafeCouponStatusToRewarded(gitchanCafeCoupon, gitchanCafePolicy.getMaxStampCount());

        // when
        List<Coupon> findCoupon = couponRepository.findCouponsByCustomerAndStatus(savedCustomer, CouponStatus.ACCUMULATING);

        // then
        assertThat(findCoupon).containsOnly(jenaCafeCoupon);
    }

    @Test
    void 임시회원의_쿠폰만_조회한다() {
        // given, when
        Cafe gitchanCafe = createCafe(OwnerFixture.GITCHAN);
        CafePolicy gitchanCafePolicy = cafePolicyRepository.save(new CafePolicy(10, "Americano", 6, gitchanCafe));
        CafeCouponDesign gitchanCafeCouponDesign = cafeCouponDesignRepository.save(new CafeCouponDesign("front", "back", "stamp", gitchanCafe));

        Customer regGit = customerRepository.save(CustomerFixture.REGISTER_CUSTOMER_GITCHAN);
        Customer tmpCustomer1 = customerRepository.save(CustomerFixture.TEMPORARY_CUSTOMER_1);
        Customer tmpCustomer2 = customerRepository.save(CustomerFixture.TEMPORARY_CUSTOMER_2);

        Coupon regGitCoupon = saveCoupon(gitchanCafe, regGit, gitchanCafeCouponDesign, gitchanCafePolicy);
        Coupon tmpCoupon1 = saveCoupon(gitchanCafe, tmpCustomer1, gitchanCafeCouponDesign, gitchanCafePolicy);
        Coupon tmpCoupon2 = saveCoupon(gitchanCafe, tmpCustomer2, gitchanCafeCouponDesign, gitchanCafePolicy);

        // then
        List<Coupon> tmpCustomerCoupons = couponRepository.findByCafeAndCustomerType(gitchanCafe, CustomerType.TEMPORARY);
        assertAll(
                () -> assertThat(tmpCustomerCoupons).containsExactlyInAnyOrder(tmpCoupon1, tmpCoupon2),
                () -> assertThat(tmpCustomerCoupons).doesNotContain(regGitCoupon)
        );
    }

    @Test
    void 가입회원의_쿠폰만_조회한다() {
        // given, when
        Cafe gitchanCafe = createCafe(OwnerFixture.GITCHAN);
        CafePolicy gitchanCafePolicy = cafePolicyRepository.save(new CafePolicy(10, "Americano", 6, gitchanCafe));
        CafeCouponDesign gitchanCafeCouponDesign = cafeCouponDesignRepository.save(new CafeCouponDesign("front", "back", "stamp", gitchanCafe));

        Customer regGit = customerRepository.save(CustomerFixture.REGISTER_CUSTOMER_GITCHAN);
        Customer tmpCustomer1 = customerRepository.save(CustomerFixture.TEMPORARY_CUSTOMER_1);
        Customer tmpCustomer2 = customerRepository.save(CustomerFixture.TEMPORARY_CUSTOMER_2);

        Coupon regGitCoupon = saveCoupon(gitchanCafe, regGit, gitchanCafeCouponDesign, gitchanCafePolicy);
        Coupon tmpCoupon1 = saveCoupon(gitchanCafe, tmpCustomer1, gitchanCafeCouponDesign, gitchanCafePolicy);
        Coupon tmpCoupon2 = saveCoupon(gitchanCafe, tmpCustomer2, gitchanCafeCouponDesign, gitchanCafePolicy);

        // then
        List<Coupon> regCustomerCoupons = couponRepository.findByCafeAndCustomerType(gitchanCafe, CustomerType.REGISTER);
        assertAll(
                () -> assertThat(regCustomerCoupons).containsExactlyInAnyOrder(regGitCoupon),
                () -> assertThat(regCustomerCoupons).doesNotContain(tmpCoupon1, tmpCoupon2)
        );
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

    private Coupon saveCoupon(Cafe savedCafe, Customer savedCustomer, CafeCouponDesign savedCouponDesign, CafePolicy savedCouponPolicy) {
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
            gitchanCafeCoupon.accumulate(1);
        }
    }
}
