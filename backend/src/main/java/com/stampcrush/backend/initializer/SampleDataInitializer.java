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
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Profile("prod")
public class SampleDataInitializer implements ApplicationRunner {
    private static final String SAMPLE_FRONT_IMAGE_URL = "https://sonoad.com/wp-content/uploads/2021/02/%EC%BB%A4%ED%94%BC-%EC%BF%A0%ED%8F%B0-%EB%A7%8C%EB%93%A4%EA%B8%B0-%EC%B9%B4%ED%8E%98%EC%BF%A0%ED%8F%B0-%EC%84%A4%EB%AA%85-1.jpg";
    private static final String SAMPLE_BACK_IMAGE_URL = "https://sonoad.com/wp-content/uploads/2021/02/%EC%BB%A4%ED%94%BC-%EC%BF%A0%ED%8F%B0-%EB%A7%8C%EB%93%A4%EA%B8%B0-%EC%B9%B4%ED%8E%98%EC%BF%A0%ED%8F%B0-%EC%84%A4%EB%AA%85-2.jpg";
    private static final String SAMPLE_STAMP_IMAGE_URL = "https://files.slack.com/files-pri/T05FM0W106M-F05FY6Y4YV6/image.png";

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
        sampleStampImageRepository.save(sampleStampImage);
    }
}
