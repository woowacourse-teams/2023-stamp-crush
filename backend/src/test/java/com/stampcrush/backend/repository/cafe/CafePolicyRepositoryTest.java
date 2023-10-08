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
import java.util.List;
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
    void 특정_카페의_정책_중_삭제되지_않은_데이터만_조회한다() {
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

        CafePolicy deletedCafePolicy = cafePolicyRepository.save(
                new CafePolicy(
                        2,
                        "마카롱",
                        12,
                        savedCafe
                )
        );
        deletedCafePolicy.delete();

        CafePolicy notDeletedCafePolicy = cafePolicyRepository.save(
                new CafePolicy(
                        2,
                        "아메리카노",
                        12,
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

    @Test
    void 특정_시간보다_이후에_생성된_카페_리워드_정책이_있으면_해당_정책들을_반환한다() {
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

        CafePolicy oldCafePolicy = cafePolicyRepository.save(
                new CafePolicy(
                        2,
                        "마카롱",
                        12,
                        savedCafe
                )
        );
        oldCafePolicy.delete();

        CafePolicy newCafePolicy = cafePolicyRepository.save(
                new CafePolicy(
                        2,
                        "아메리카노",
                        12,
                        savedCafe
                )
        );

        List<CafePolicy> cafePolicies = cafePolicyRepository.findByCafeAndCreatedAtGreaterThan(savedCafe, oldCafePolicy.getCreatedAt());
        assertThat(cafePolicies).isNotEmpty();
    }

    @Test
    void 특정_시간보다_이후에_생성된_카페_리워드_정책이_없으면_빈_리스트_반환() {
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

        CafePolicy currentPolicy = cafePolicyRepository.save(
                new CafePolicy(
                        2,
                        "마카롱",
                        12,
                        savedCafe
                )
        );

        List<CafePolicy> cafePolicies = cafePolicyRepository.findByCafeAndCreatedAtGreaterThan(savedCafe, currentPolicy.getCreatedAt());
        assertThat(cafePolicies).isEmpty();
    }
}
