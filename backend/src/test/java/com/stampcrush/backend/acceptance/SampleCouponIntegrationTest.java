package com.stampcrush.backend.acceptance;

import com.stampcrush.backend.entity.sample.SampleBackImage;
import com.stampcrush.backend.entity.sample.SampleFrontImage;
import com.stampcrush.backend.entity.sample.SampleStampImage;
import com.stampcrush.backend.repository.sample.SampleBackImageRepository;
import com.stampcrush.backend.repository.sample.SampleFrontImageRepository;
import com.stampcrush.backend.repository.sample.SampleStampCoordinateRepository;
import com.stampcrush.backend.repository.sample.SampleStampImageRepository;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import static com.stampcrush.backend.fixture.SampleCouponFixture.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class SampleCouponIntegrationTest extends IntegrationTest {

    @Autowired
    private SampleFrontImageRepository sampleFrontImageRepository;

    @Autowired
    private SampleBackImageRepository sampleBackImageRepository;

    @Autowired
    private SampleStampCoordinateRepository sampleStampCoordinateRepository;

    @Autowired
    private SampleStampImageRepository sampleStampImageRepository;

    @Override
    @BeforeEach
    void setUp() {
        sampleFrontImageRepository.deleteAll();
        sampleStampCoordinateRepository.deleteAll();
        sampleBackImageRepository.deleteAll();
        sampleStampImageRepository.deleteAll();

        saveSampleCoupon();
    }

    private void saveSampleCoupon() {
        SampleFrontImage savedSampleFrontImage = sampleFrontImageRepository.save(SAMPLE_FRONT_IMAGE);
        SampleBackImage savedSampleBackImage = sampleBackImageRepository.save(SAMPLE_BACK_IMAGE);
        SAMPLE_COORDINATES_SIZE_EIGHT.stream()
                .map(e -> sampleStampCoordinateRepository.save(e))
                .forEach(e -> e.setSampleBackImage(savedSampleBackImage));
        SampleStampImage savedSampleStampImage = sampleStampImageRepository.save(SAMPLE_STAMP_IMAGE);
    }

    @Test
    void 전체_쿠폰_샘플을_조회한다() {
        // given, when
        ExtractableResponse<Response> response = RestAssured.given()
                .log().all()

                .when()
                .get("/coupon-samples")

                .then()
                .extract();

        // then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(response.jsonPath().getList("sampleFrontImages").size()).isEqualTo(1),
                () -> assertThat(response.jsonPath().getList("sampleBackImages").size()).isEqualTo(1),
                () -> assertThat(response.jsonPath().getList("sampleStampImages").size()).isEqualTo(1)
        );
    }

    @Test
    void 최대_스탬프_개수로_쿠폰_샘플을_필터링해서_조회한다() {
        // given, when
        ExtractableResponse<Response> response = RestAssured.given()
                .log().all()

                .when()
                .get("/coupon-samples?maxStampCount=8")

                .then()
                .extract();

        // then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(response.jsonPath().getList("sampleFrontImages").size()).isEqualTo(1),
                () -> assertThat(response.jsonPath().getList("sampleBackImages").size()).isEqualTo(1),
                () -> assertThat(response.jsonPath().getList("sampleStampImages").size()).isEqualTo(1)
        );
    }

    @Test
    void 해당하는_최대_스탬프_개수의_샘플_쿠폰이_없으면_뒷면이_조회되지_않는다() {
        // given, when
        ExtractableResponse<Response> response = RestAssured.given()
                .log().all()

                .when()
                .get("/coupon-samples?maxStampCount=10")

                .then()
                .extract();

        // then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(response.jsonPath().getList("sampleFrontImages").size()).isEqualTo(1),
                () -> assertThat(response.jsonPath().getList("sampleBackImages").size()).isEqualTo(0),
                () -> assertThat(response.jsonPath().getList("sampleStampImages").size()).isEqualTo(1)
        );
    }
}
