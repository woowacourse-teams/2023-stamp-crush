package com.stampcrush.backend.repository.coupon;

import com.stampcrush.backend.entity.coupon.CouponDesign;
import com.stampcrush.backend.entity.coupon.CouponStampCoordinate;
import com.stampcrush.backend.fixture.CouponDesignFixture;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest
class CouponStampCoordinateRepositoryTest {

    @Autowired
    private CouponStampCoordinateRepository couponStampCoordinateRepository;

    @Autowired
    private CouponDesignRepository couponDesignRepository;

    @Test
    void 쿠폰의_좌표를_쿠폰_디자인으로_필터링해서_조회한다() {
        // given, when
        CouponDesign couponDesign1 = couponDesignRepository.save(CouponDesignFixture.COUPON_DESIGN_1);
        CouponDesign couponDesign2 = couponDesignRepository.save(CouponDesignFixture.COUPON_DESIGN_2);

        CouponStampCoordinate couponStampCoordinateOfCouponDesign1 = couponStampCoordinateRepository.save(new CouponStampCoordinate(1, 1, 1, couponDesign1));

        List<CouponStampCoordinate> coordinatesOfCouponDesign1 = couponStampCoordinateRepository.findByCouponDesign(couponDesign1);
        List<CouponStampCoordinate> coordinatesOfCouponDesign2 = couponStampCoordinateRepository.findByCouponDesign(couponDesign2);

        assertAll(
                () -> assertThat(coordinatesOfCouponDesign1).isNotEmpty(),
                () -> assertThat(coordinatesOfCouponDesign1).containsExactlyInAnyOrder(couponStampCoordinateOfCouponDesign1),
                () -> assertThat(coordinatesOfCouponDesign2).isEmpty()
        );
    }
}
