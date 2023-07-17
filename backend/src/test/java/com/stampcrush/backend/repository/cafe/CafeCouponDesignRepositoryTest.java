package com.stampcrush.backend.repository.cafe;

import com.stampcrush.backend.entity.cafe.Cafe;
import com.stampcrush.backend.entity.cafe.CafeCouponDesign;
import com.stampcrush.backend.entity.user.Owner;
import com.stampcrush.backend.repository.user.OwnerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest
class CafeCouponDesignRepositoryTest {

    @Autowired
    private CafeCouponDesignRepository cafeCouponDesignRepository;

    @Autowired
    private CafeRepository cafeRepository;

    @Autowired
    private OwnerRepository ownerRepository;

    @Test
    void 특정_카페의_디자인_중_삭제되지_않은_데이터만_조회한다() {
        // given, when
        Cafe savedCafe = cafeRepository.save(
                new Cafe(
                        "깃짱카페",
                        LocalTime.NOON,
                        LocalTime.MIDNIGHT,
                        "01012345678",
                        "#",
                        "서울시 올림픽로 어쩌고",
                        "루터회관",
                        "10-222-333",
                        ownerRepository.save(new Owner("이름", "아이디", "pw", "phone"))
                )
        );

        CafeCouponDesign deletedCafeCouponDesign = cafeCouponDesignRepository.save(
                new CafeCouponDesign(
                        "#", "#", "#", true, savedCafe
                )
        );

        CafeCouponDesign notDeletedCafeCouponDesign = cafeCouponDesignRepository.save(
                new CafeCouponDesign(
                        "#", "#", "#", false, savedCafe
                )
        );

        Optional<CafeCouponDesign> filteredCafeCouponDesign = cafeCouponDesignRepository.findByCafeAndDeletedIsFalse(savedCafe);

        // then
        assertAll(
                () -> assertThat(filteredCafeCouponDesign).isNotEmpty(),
                () -> assertThat(filteredCafeCouponDesign.get()).isEqualTo(notDeletedCafeCouponDesign),
                () -> assertThat(filteredCafeCouponDesign.get()).isNotEqualTo(deletedCafeCouponDesign)
        );
    }
}
