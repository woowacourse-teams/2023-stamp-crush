package com.stampcrush.backend.application.manager.sample;

import com.stampcrush.backend.application.manager.sample.dto.SampleCouponsFindResultDto;
import com.stampcrush.backend.common.KorNamingConverter;
import com.stampcrush.backend.entity.sample.SampleBackImage;
import com.stampcrush.backend.entity.sample.SampleFrontImage;
import com.stampcrush.backend.entity.sample.SampleStampCoordinate;
import com.stampcrush.backend.entity.sample.SampleStampImage;
import com.stampcrush.backend.fixture.SampleCouponFixture;
import com.stampcrush.backend.repository.sample.SampleBackImageRepository;
import com.stampcrush.backend.repository.sample.SampleFrontImageRepository;
import com.stampcrush.backend.repository.sample.SampleStampCoordinateRepository;
import com.stampcrush.backend.repository.sample.SampleStampImageRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;

@KorNamingConverter
@ExtendWith(MockitoExtension.class)
class ManagerSampleCouponFindServiceTest {

    @InjectMocks
    private ManagerSampleCouponFindService managerSampleCouponFindService;

    @Mock
    private SampleFrontImageRepository sampleFrontImageRepository;

    @Mock
    private SampleBackImageRepository sampleBackImageRepository;

    @Mock
    private SampleStampCoordinateRepository sampleStampCoordinateRepository;

    @Mock
    private SampleStampImageRepository sampleStampImageRepository;

    @Test
    void 샘플_쿠폰을_조회한다() {
        List<SampleFrontImage> sampleFrontImage = List.of(SampleCouponFixture.SAMPLE_FRONT_IMAGE);
        List<SampleBackImage> sampleBackImage = List.of(SampleCouponFixture.SAMPLE_BACK_IMAGE);
        List<SampleStampImage> sampleStampImage = List.of(SampleCouponFixture.SAMPLE_STAMP_IMAGE);
        List<SampleStampCoordinate> sampleStampCoordinates = SampleCouponFixture.SAMPLE_COORDINATES_SIZE_EIGHT;

        when(sampleFrontImageRepository.findAll())
                .thenReturn(sampleFrontImage);
        when(sampleBackImageRepository.findAll())
                .thenReturn(sampleBackImage);
        when(sampleStampImageRepository.findAll())
                .thenReturn(sampleStampImage);
        when(sampleStampCoordinateRepository.findSampleStampCoordinateBySampleBackImage(SampleCouponFixture.SAMPLE_BACK_IMAGE))
                .thenReturn(sampleStampCoordinates);

        int maxStampCount = 8;

        assertAll(
                () -> assertThat(managerSampleCouponFindService.findSampleCouponsByMaxStampCount(maxStampCount)).isNotNull(),
                () -> assertThat(managerSampleCouponFindService.findSampleCouponsByMaxStampCount(maxStampCount))
                        .usingRecursiveComparison()
                        .isEqualTo(SampleCouponsFindResultDto.of(
                                sampleFrontImage,
                                sampleBackImage,
                                sampleStampCoordinates,
                                sampleStampImage
                        ))
        );
    }
}
