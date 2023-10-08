package com.stampcrush.backend.repository.cafe;

import com.stampcrush.backend.common.KorNamingConverter;
import com.stampcrush.backend.entity.cafe.Cafe;
import com.stampcrush.backend.entity.cafe.CafeCouponDesign;
import com.stampcrush.backend.entity.cafe.CafeStampCoordinate;
import com.stampcrush.backend.entity.user.Owner;
import com.stampcrush.backend.fixture.OwnerFixture;
import com.stampcrush.backend.repository.user.OwnerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@KorNamingConverter
@DataJpaTest
class CafeStampCoordinateRepositoryTest {

    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private CafeRepository cafeRepository;

    @Autowired
    private CafeStampCoordinateRepository cafeStampCoordinateRepository;

    @Autowired
    private CafeCouponDesignRepository cafeCouponDesignRepository;

    @Test
    void 쿠폰의_좌표를_쿠폰_디자인으로_필터링해서_조회한다() {
        // given, when
        Cafe gitchanCafe = createCafe(OwnerFixture.GITCHAN);
        Cafe jenaCafe = createCafe(OwnerFixture.JENA);

        CafeCouponDesign gitchanCafeCouponDesign = cafeCouponDesignRepository.save(new CafeCouponDesign("front", "back", "stamp", gitchanCafe));
        CafeCouponDesign jenaCafeCouponDesign = cafeCouponDesignRepository.save(new CafeCouponDesign("front", "back", "stamp", jenaCafe));

        CafeStampCoordinate couponStampCoordinateOfCouponDesign1 = cafeStampCoordinateRepository.save(new CafeStampCoordinate(1, 1, 1, gitchanCafeCouponDesign));

        List<CafeStampCoordinate> coordinatesOfCouponDesign1 = cafeStampCoordinateRepository.findByCafeCouponDesign(gitchanCafeCouponDesign);
        List<CafeStampCoordinate> coordinatesOfCouponDesign2 = cafeStampCoordinateRepository.findByCafeCouponDesign(jenaCafeCouponDesign);

        assertAll(
                () -> assertThat(coordinatesOfCouponDesign1).isNotEmpty(),
                () -> assertThat(coordinatesOfCouponDesign1).containsExactlyInAnyOrder(couponStampCoordinateOfCouponDesign1),
                () -> assertThat(coordinatesOfCouponDesign2).isEmpty()
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
}
