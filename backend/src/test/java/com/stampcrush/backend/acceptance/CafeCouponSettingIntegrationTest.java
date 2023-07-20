package com.stampcrush.backend.acceptance;

import com.stampcrush.backend.api.cafe.request.CafeCouponSettingUpdateRequest;
import com.stampcrush.backend.entity.cafe.Cafe;
import com.stampcrush.backend.entity.cafe.CafeCouponDesign;
import com.stampcrush.backend.entity.cafe.CafePolicy;
import com.stampcrush.backend.entity.cafe.CafeStampCoordinate;
import com.stampcrush.backend.entity.user.Owner;
import com.stampcrush.backend.repository.cafe.CafeCouponDesignRepository;
import com.stampcrush.backend.repository.cafe.CafePolicyRepository;
import com.stampcrush.backend.repository.cafe.CafeRepository;
import com.stampcrush.backend.repository.cafe.CafeStampCoordinateRepository;
import com.stampcrush.backend.repository.user.OwnerRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class CafeCouponSettingIntegrationTest extends IntegrationTest {

    @Autowired
    private CafeRepository cafeRepository;

    @Autowired
    private CafeCouponDesignRepository cafeCouponDesignRepository;

    @Autowired
    private CafePolicyRepository cafePolicyRepository;

    @Autowired
    private CafeStampCoordinateRepository cafeStampCoordinateRepository;

    @Autowired
    private OwnerRepository ownerRepository;

    @Override
    @BeforeEach
    void setUp() {
        super.setUp();
        cafePolicyRepository.deleteAll();
        cafeStampCoordinateRepository.deleteAll();
        cafeCouponDesignRepository.deleteAll();
    }

    @Test
    void 카페_사장은_쿠폰_세팅에_대한_내용을_수정할_수_있다() {
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
                        ownerRepository.save(
                                new Owner(
                                        "깃짱",
                                        "깃짱아이디",
                                        "깃짱비밀번호",
                                        "깃짱핸드폰번호")
                        )
                )
        );

        CafePolicy savedCafePolicy = cafePolicyRepository.save(
                new CafePolicy(
                        2,
                        "아메리카노",
                        12,
                        false,
                        savedCafe
                )
        );

        CafeCouponDesign savedCafeCouponDesign = cafeCouponDesignRepository.save(
                new CafeCouponDesign(
                        "#",
                        "#",
                        "#",
                        false,
                        savedCafe
                )
        );

        CafeStampCoordinate savedCafeStampCoordinate1 = cafeStampCoordinateRepository.save(
                new CafeStampCoordinate(
                        1, 1, 1, savedCafeCouponDesign
                )
        );

        CafeStampCoordinate savedCafeStampCoordinate2 = cafeStampCoordinateRepository.save(
                new CafeStampCoordinate(
                        1, 2, 1, savedCafeCouponDesign
                )
        );

        CafeCouponSettingUpdateRequest request = new CafeCouponSettingUpdateRequest(
                "newFrontImageUrl",
                "newBackImageUrl",
                "newStampImageUrl",
                List.of(new CafeCouponSettingUpdateRequest.CouponStampCoordinateRequest(1, 2, 1)),
                "newReward",
                12
        );

        ExtractableResponse<Response> response = RestAssured.given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(request)

                .when()
                .post("/api/coupon-setting?cafe-id=" + savedCafe.getId())

                .then()
                .log().all()
                .extract();

        // then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value()),
                () -> assertThat(cafeCouponDesignRepository.findById(savedCafeCouponDesign.getId())).isEmpty(),
                () -> assertThat(cafeCouponDesignRepository.findByCafeAndDeletedIsFalse(savedCafe)).isNotEmpty(),
                () -> assertThat(cafePolicyRepository.findById(savedCafePolicy.getId())).isEmpty(),
                () -> assertThat(cafePolicyRepository.findByCafeAndDeletedIsFalse(savedCafe)).isNotEmpty()
        );
    }
}
