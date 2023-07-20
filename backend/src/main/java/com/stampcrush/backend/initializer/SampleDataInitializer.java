package com.stampcrush.backend.initializer;

import com.stampcrush.backend.entity.sample.SampleBackImage;
import com.stampcrush.backend.entity.sample.SampleFrontImage;
import com.stampcrush.backend.entity.sample.SampleStampCoordinate;
import com.stampcrush.backend.entity.sample.SampleStampImage;
import com.stampcrush.backend.repository.sample.SampleBackImageRepository;
import com.stampcrush.backend.repository.sample.SampleFrontImageRepository;
import com.stampcrush.backend.repository.sample.SampleStampCoordinateRepository;
import com.stampcrush.backend.repository.sample.SampleStampImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Profile("dev")
@Order(1)
public class SampleDataInitializer implements ApplicationRunner {

    private static final String SAMPLE_FRONT_IMAGE_URL = "frontImageUrl";
    private static final String SAMPLE_BACK_IMAGE_URL = "backImageUrl";
    private static final String SAMPLE_BACK_IMAGE_URL_2 = "backImageUrl_stamp10";
    private static final String SAMPLE_STAMP_IMAGE_URL = "sampleImageUrl";

    private final SampleFrontImageRepository sampleFrontImageRepository;
    private final SampleBackImageRepository sampleBackImageRepository;
    private final SampleStampCoordinateRepository sampleStampCoordinateRepository;
    private final SampleStampImageRepository sampleStampImageRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        SampleFrontImage sampleFrontImage = new SampleFrontImage(SAMPLE_FRONT_IMAGE_URL);
        SampleFrontImage savedFrontImage = sampleFrontImageRepository.save(sampleFrontImage);

        SampleBackImage sampleBackImage = new SampleBackImage(SAMPLE_BACK_IMAGE_URL);
        SampleBackImage savedSampleBackImage = sampleBackImageRepository.save(sampleBackImage);

        sampleStampCoordinateRepository.save(new SampleStampCoordinate(1, 1, 2, sampleBackImage));
        sampleStampCoordinateRepository.save(new SampleStampCoordinate(2, 2, 2, sampleBackImage));
        sampleStampCoordinateRepository.save(new SampleStampCoordinate(3, 3, 2, sampleBackImage));
        sampleStampCoordinateRepository.save(new SampleStampCoordinate(4, 4, 2, sampleBackImage));
        sampleStampCoordinateRepository.save(new SampleStampCoordinate(5, 1, 1, sampleBackImage));
        sampleStampCoordinateRepository.save(new SampleStampCoordinate(6, 2, 1, sampleBackImage));
        sampleStampCoordinateRepository.save(new SampleStampCoordinate(7, 3, 1, sampleBackImage));
        sampleStampCoordinateRepository.save(new SampleStampCoordinate(8, 4, 1, sampleBackImage));

        SampleStampImage sampleStampImage = new SampleStampImage(SAMPLE_STAMP_IMAGE_URL);

        SampleBackImage sampleBackImage_2 = sampleBackImageRepository.save(new SampleBackImage(SAMPLE_BACK_IMAGE_URL_2));

        sampleStampCoordinateRepository.save(new SampleStampCoordinate(1, 1, 2, sampleBackImage_2));
        sampleStampCoordinateRepository.save(new SampleStampCoordinate(2, 2, 2, sampleBackImage_2));
        sampleStampCoordinateRepository.save(new SampleStampCoordinate(3, 3, 2, sampleBackImage_2));
        sampleStampCoordinateRepository.save(new SampleStampCoordinate(4, 4, 2, sampleBackImage_2));
        sampleStampCoordinateRepository.save(new SampleStampCoordinate(5, 1, 1, sampleBackImage_2));
        sampleStampCoordinateRepository.save(new SampleStampCoordinate(6, 2, 1, sampleBackImage_2));
        sampleStampCoordinateRepository.save(new SampleStampCoordinate(7, 3, 1, sampleBackImage_2));
        sampleStampCoordinateRepository.save(new SampleStampCoordinate(8, 4, 1, sampleBackImage_2));
        sampleStampCoordinateRepository.save(new SampleStampCoordinate(9, 4, 1, sampleBackImage_2));
        sampleStampCoordinateRepository.save(new SampleStampCoordinate(10, 4, 1, sampleBackImage_2));

        sampleStampImageRepository.save(sampleStampImage);
    }
}
