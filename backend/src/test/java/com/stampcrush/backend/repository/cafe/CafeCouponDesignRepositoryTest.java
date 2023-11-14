package com.stampcrush.backend.repository.cafe;

import com.stampcrush.backend.common.KorNamingConverter;
import com.stampcrush.backend.entity.cafe.Cafe;
import com.stampcrush.backend.entity.cafe.CafeCouponDesign;
import com.stampcrush.backend.entity.user.Owner;
import com.stampcrush.backend.fixture.OwnerFixture;
import com.stampcrush.backend.repository.user.OwnerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@KorNamingConverter
@DataJpaTest
class CafeCouponDesignRepositoryTest {

    @Autowired
    private CafeCouponDesignRepository cafeCouponDesignRepository;

    @Autowired
    private CafeRepository cafeRepository;

    @Autowired
    private OwnerRepository ownerRepository;

    @Test
    void 활성화된_쿠폰만_조회한다() {
        // given, when
        Cafe savedCafe = createCafe(OwnerFixture.GITCHAN);

        CafeCouponDesign deletedCafeCouponDesign = saveCafeCouponDesign(savedCafe, true);
        CafeCouponDesign notDeletedCafeCouponDesign = saveCafeCouponDesign(savedCafe, false);

        Optional<CafeCouponDesign> filteredCafeCouponDesign = cafeCouponDesignRepository.findByCafeAndIsActivateTrue(savedCafe);

        // then
        assertAll(
                () -> assertThat(filteredCafeCouponDesign).isNotEmpty(),
                () -> assertThat(filteredCafeCouponDesign.get()).isEqualTo(notDeletedCafeCouponDesign),
                () -> assertThat(filteredCafeCouponDesign.get()).isNotEqualTo(deletedCafeCouponDesign)
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

    private CafeCouponDesign saveCafeCouponDesign(Cafe savedCafe, boolean deleted) {
        return cafeCouponDesignRepository.save(
                new CafeCouponDesign(
                        "#", "#", "#", deleted, savedCafe
                )
        );
    }
}
