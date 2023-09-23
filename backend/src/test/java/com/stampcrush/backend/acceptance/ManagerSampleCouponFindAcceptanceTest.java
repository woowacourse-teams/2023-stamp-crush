package com.stampcrush.backend.acceptance;

import com.stampcrush.backend.auth.application.util.AuthTokensGenerator;
import com.stampcrush.backend.entity.sample.SampleBackImage;
import com.stampcrush.backend.repository.sample.SampleBackImageRepository;
import com.stampcrush.backend.repository.sample.SampleFrontImageRepository;
import com.stampcrush.backend.repository.sample.SampleStampCoordinateRepository;
import com.stampcrush.backend.repository.sample.SampleStampImageRepository;
import com.stampcrush.backend.repository.user.OwnerRepository;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import static com.stampcrush.backend.acceptance.step.ManagerJoinStep.OWNER_CREATE_REQUEST;
import static com.stampcrush.backend.acceptance.step.ManagerJoinStep.카페_사장_회원_가입_요청하고_액세스_토큰_반환;
import static com.stampcrush.backend.acceptance.step.ManagerSampleCouponFindStep.샘플_쿠폰_조회_요청;
import static com.stampcrush.backend.fixture.SampleCouponFixture.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class ManagerSampleCouponFindAcceptanceTest extends AcceptanceTest {

    @Autowired
    private SampleFrontImageRepository sampleFrontImageRepository;

    @Autowired
    private SampleBackImageRepository sampleBackImageRepository;

    @Autowired
    private SampleStampCoordinateRepository sampleStampCoordinateRepository;

    @Autowired
    private SampleStampImageRepository sampleStampImageRepository;

    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private AuthTokensGenerator authTokensGenerator;

    @BeforeEach
    void setUp() {
        sampleFrontImageRepository.save(SAMPLE_FRONT_IMAGE);
        SampleBackImage savedSampleBackImage = sampleBackImageRepository.save(SAMPLE_BACK_IMAGE);
        SAMPLE_COORDINATES_SIZE_EIGHT.stream()
                .peek(e -> e.setSampleBackImage(savedSampleBackImage))
                .forEach(e -> sampleStampCoordinateRepository.save(e));
        sampleStampImageRepository.save(SAMPLE_STAMP_IMAGE);
    }

    @Test
    void 최대_스탬프_개수로_쿠폰_샘플을_필터링해서_조회한다() {
        // given, when
        String accessToken = 카페_사장_회원_가입_요청하고_액세스_토큰_반환(OWNER_CREATE_REQUEST);

        ExtractableResponse<Response> response = 샘플_쿠폰_조회_요청(accessToken, 8);

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
        String accessToken = 카페_사장_회원_가입_요청하고_액세스_토큰_반환(OWNER_CREATE_REQUEST);

        ExtractableResponse<Response> response = 샘플_쿠폰_조회_요청(accessToken, 10);

        // then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(response.jsonPath().getList("sampleFrontImages").size()).isEqualTo(1),
                () -> assertThat(response.jsonPath().getList("sampleBackImages").size()).isEqualTo(0),
                () -> assertThat(response.jsonPath().getList("sampleStampImages").size()).isEqualTo(1)
        );
    }
}
