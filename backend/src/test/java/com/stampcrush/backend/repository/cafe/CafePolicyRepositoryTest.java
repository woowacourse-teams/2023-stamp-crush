package com.stampcrush.backend.repository.cafe;

import com.stampcrush.backend.common.KorNamingConverter;
import com.stampcrush.backend.entity.cafe.Cafe;
import com.stampcrush.backend.entity.cafe.CafePolicy;
import com.stampcrush.backend.entity.user.Owner;
import com.stampcrush.backend.repository.user.OwnerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.time.LocalTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@KorNamingConverter
@EnableJpaAuditing
@DataJpaTest
class CafePolicyRepositoryTest {

    @Autowired
    private CafePolicyRepository cafePolicyRepository;

    @Autowired
    private CafeRepository cafeRepository;

    @Autowired
    private OwnerRepository ownerRepository;

    @Test
    void 특정_카페의_정책_중_활성화된_데이터만_조회한다() {
        // given, when
        Cafe savedCafe = cafeRepository.save(
                new Cafe(
                        "깃짱카페",
                        LocalTime.NOON,
                        LocalTime.MIDNIGHT,
                        "01012345678",
                        "#",
                        "안녕하세요",
                        "서울시 올림픽로 어쩌고",
                        "루터회관",
                        "10-222-333",
                        ownerRepository.save(new Owner("이름", "아이디", "pw", "phone"))
                )
        );

        CafePolicy disableCafePolicy = cafePolicyRepository.save(
                new CafePolicy(
                        2,
                        "마카롱",
                        12,
                        savedCafe
                )
        );
        disableCafePolicy.disable();

        CafePolicy activeCafePolicy = cafePolicyRepository.save(
                new CafePolicy(
                        2,
                        "아메리카노",
                        12,
                        savedCafe
                )
        );

        Optional<CafePolicy> filteredCafePolicy = cafePolicyRepository.findByCafeAndIsActivateTrue(savedCafe);

        // then
        assertAll(
                () -> assertThat(filteredCafePolicy).isNotEmpty(),
                () -> assertThat(filteredCafePolicy.get()).isEqualTo(activeCafePolicy),
                () -> assertThat(filteredCafePolicy.get()).isNotEqualTo(disableCafePolicy)
        );
    }
}
