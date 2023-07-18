package com.stampcrush.backend.repository.cafe;

import com.stampcrush.backend.entity.cafe.Cafe;
import com.stampcrush.backend.entity.cafe.CafePolicy;
import com.stampcrush.backend.entity.user.Owner;
import com.stampcrush.backend.repository.user.OwnerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalTime;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest
class CafePolicyRepositoryTest {

    @Autowired
    private CafePolicyRepository cafePolicyRepository;

    @Autowired
    private CafeRepository cafeRepository;

    @Autowired
    private OwnerRepository ownerRepository;

    @Test
    void 특정_카페의_정책_중_삭제되지_않은_데이터만_조회한다() {
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

        CafePolicy deletedCafePolicy = cafePolicyRepository.save(
                new CafePolicy(
                        2,
                        "마카롱",
                        12,
                        true,
                        savedCafe
                )
        );

        CafePolicy notDeletedCafePolicy = cafePolicyRepository.save(
                new CafePolicy(
                        2,
                        "아메리카노",
                        12,
                        false,
                        savedCafe
                )
        );

        Optional<CafePolicy> filteredCafePolicy = cafePolicyRepository.findByCafeAndDeletedIsFalse(savedCafe);

        // then
        assertAll(
                () -> assertThat(filteredCafePolicy).isNotEmpty(),
                () -> assertThat(filteredCafePolicy.get()).isEqualTo(notDeletedCafePolicy),
                () -> assertThat(filteredCafePolicy.get()).isNotEqualTo(deletedCafePolicy)
        );
    }
}
