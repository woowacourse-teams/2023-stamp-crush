package com.stampcrush.backend.application.cafe;

import com.stampcrush.backend.application.cafe.dto.CafeCreateDto;
import com.stampcrush.backend.entity.cafe.Cafe;
import com.stampcrush.backend.entity.cafe.CafeCouponDesign;
import com.stampcrush.backend.entity.cafe.CafePolicy;
import com.stampcrush.backend.entity.cafe.CafeStampCoordinate;
import com.stampcrush.backend.entity.sample.SampleBackImage;
import com.stampcrush.backend.entity.sample.SampleFrontImage;
import com.stampcrush.backend.entity.sample.SampleStampCoordinate;
import com.stampcrush.backend.entity.sample.SampleStampImage;
import com.stampcrush.backend.entity.user.Owner;
import com.stampcrush.backend.repository.cafe.CafeCouponDesignRepository;
import com.stampcrush.backend.repository.cafe.CafePolicyRepository;
import com.stampcrush.backend.repository.cafe.CafeRepository;
import com.stampcrush.backend.repository.cafe.CafeStampCoordinateRepository;
import com.stampcrush.backend.repository.sample.SampleBackImageRepository;
import com.stampcrush.backend.repository.sample.SampleFrontImageRepository;
import com.stampcrush.backend.repository.sample.SampleStampCoordinateRepository;
import com.stampcrush.backend.repository.sample.SampleStampImageRepository;
import com.stampcrush.backend.repository.user.OwnerRepository;
import jakarta.persistence.EntityManager;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
public class CafeServiceTest {

    @Autowired
    private EntityManager em;

    @Autowired
    private CafeService cafeService;

    @Autowired
    private CafeRepository cafeRepository;

    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private SampleFrontImageRepository sampleFrontImageRepository;

    @Autowired
    private SampleBackImageRepository sampleBackImageRepository;

    @Autowired
    private SampleStampImageRepository sampleStampImageRepository;

    @Autowired
    private SampleStampCoordinateRepository sampleStampCoordinateRepository;

    @Autowired
    private CafePolicyRepository cafePolicyRepository;

    @Autowired
    private CafeCouponDesignRepository cafeCouponDesignRepository;

    @Autowired
    private CafeStampCoordinateRepository cafeStampCoordinateRepository;

    private Owner owner_1;
    private Owner owner_2;
    private SampleFrontImage sampleFrontImage;
    private SampleBackImage sampleBackImage;
    private SampleStampImage sampleStampImage;

    @BeforeEach
    void setUp() {
        owner_1 = ownerRepository.save(new Owner("lisa", "lisa@naver.com", "1234", "01011111111"));
        owner_2 = ownerRepository.save(new Owner("hardy", "ehdgur4814@naver.com", "1234", "01011111111"));
        sampleFrontImage = sampleFrontImageRepository.save(new SampleFrontImage("http://www.sampleFrontImage.com"));
        sampleBackImage = sampleBackImageRepository.save(new SampleBackImage("http://www.sampleBackImage.com"));
        sampleStampImage = sampleStampImageRepository.save(new SampleStampImage("http://www.sampleStampImage.com"));
        for (int i = 1; i <= 10; i++) {
            sampleStampCoordinateRepository.save(new SampleStampCoordinate(i, i, i, sampleBackImage));
        }
        em.flush();
        em.clear();
    }

    @Test
    void 카페를_등록한다() {
        // given
        CafeCreateDto cafeCreateDto = getCafeCreateDto();

        // when
        Long cafeId = cafeService.createCafe(cafeCreateDto);

        // then
        assertThat(cafeId).isNotNull();
    }

    @Test
    @Disabled
    void 카페를_등록할_때_쿠폰이미지는_기본이미지로_저장된다() {
        // given
        CafeCreateDto cafeCreateDto = getCafeCreateDto();

        // when
        Long cafeId = cafeService.createCafe(cafeCreateDto);
        Cafe cafe = cafeRepository.findById(cafeId).get();
        Optional<CafeCouponDesign> cafeCouponDesign = cafeCouponDesignRepository.findByCafe(cafe)
                .stream()
                .filter(design -> design.getCafe().getId().equals(cafeId))
                .findAny();

        // then
        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(cafeCouponDesign).isPresent();
        softAssertions.assertThat(cafeCouponDesign.get().getFrontImageUrl()).isEqualTo(sampleFrontImage.getImageUrl());
        softAssertions.assertThat(cafeCouponDesign.get().getBackImageUrl()).isEqualTo(sampleBackImage.getImageUrl());
        softAssertions.assertThat(cafeCouponDesign.get().getStampImageUrl()).isEqualTo(sampleStampImage.getImageUrl());
        softAssertions.assertAll();
    }

    @Test
    @Disabled
    void 카페를_등록할_때_스탬프_좌표는_10개가_저장된다() {
        // given
        Integer expectedStampCoordinatesCount = 10;
        CafeCreateDto cafeCreateDto = getCafeCreateDto();

        // when
        Long cafeId = cafeService.createCafe(cafeCreateDto);
        CafeCouponDesign cafeCouponDesign = cafeCouponDesignRepository.findAll()
                .stream()
                .filter(design -> design.getCafe().getId().equals(cafeId))
                .findAny()
                .get();
        List<CafeStampCoordinate> cafeStampCoordinates = cafeStampCoordinateRepository.findAll()
                .stream()
                .filter(stamp -> stamp.getCafeCouponDesign().equals(cafeCouponDesign))
                .toList();

        // then
        assertThat(cafeStampCoordinates.size()).isEqualTo(expectedStampCoordinatesCount);
    }

    /**
     * 기본정책
     * 1. 최대 스탬프 개수: 10
     * 2. 유효기간: 6
     */
    @Test
    void 카페를_등록할_때_정책은_기본정책으로_저장된다() {
        // given
        Integer expectedMaxStampCount = 10;
        Integer expectedExpirePeriod = 6;
        CafeCreateDto cafeCreateDto = getCafeCreateDto();

        // when
        Long cafeId = cafeService.createCafe(cafeCreateDto);
        Optional<CafePolicy> cafePolicy = cafePolicyRepository.findAll()
                .stream()
                .filter(policy -> policy.getCafe().getId().equals(cafeId))
                .findAny();

        // then
        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(cafePolicy).isPresent();
        softAssertions.assertThat(cafePolicy.get().getMaxStampCount()).isEqualTo(expectedMaxStampCount);
        softAssertions.assertThat(cafePolicy.get().getExpirePeriod()).isEqualTo(expectedExpirePeriod);
        softAssertions.assertThat(cafePolicy.get().getDeleted()).isFalse();
        softAssertions.assertAll();
    }

    private CafeCreateDto getCafeCreateDto() {
        return new CafeCreateDto(
                owner_1.getId(),
                "윤생까페",
                "잠실동12길",
                "14층",
                "11111111");
    }

    @Test
    void 카페목록을_조회한다() {
        // given
        Integer expectedSize = 2;
        createCafe();
        createCafe();
        // when
        List<Cafe> cafes = cafeRepository.findAllByOwnerId(owner_2.getId());

        // then
        assertThat(cafes.size()).isEqualTo(expectedSize);
    }

    @Test
    void 카페를_소유하지_않은_사장이_카페목록을_조회하면_빈_배열을_반환한다() {
        // given
        Integer expectedSize = 0;

        // when
        List<Cafe> cafes = cafeRepository.findAllByOwnerId(owner_2.getId());

        // then
        assertThat(cafes.size()).isEqualTo(expectedSize);
    }

    private void createCafe() {
        cafeRepository.save(
                new Cafe(
                        "하디까페",
                        LocalTime.of(12, 30),
                        LocalTime.of(18, 30),
                        "0211111111",
                        "http://www.cafeImage.com",
                        "잠실동12길",
                        "14층",
                        "11111111",
                        owner_2));
    }
}
