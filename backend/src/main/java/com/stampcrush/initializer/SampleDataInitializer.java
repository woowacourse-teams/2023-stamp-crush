package com.stampcrush.initializer;

import com.stampcrush.backend.entity.sample.SampleBackImage;
import com.stampcrush.backend.entity.sample.SampleFrontImage;
import com.stampcrush.backend.entity.sample.SampleStampCoordinate;
import com.stampcrush.backend.entity.sample.SampleStampImage;
import com.stampcrush.backend.repository.sample.SampleBackImageRepository;
import com.stampcrush.backend.repository.sample.SampleFrontImageRepository;
import com.stampcrush.backend.repository.sample.SampleStampCoordinateRepository;
import com.stampcrush.backend.repository.sample.SampleStampImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class SampleDataInitializer {
    private static final String SAMPLE_FRONT_IMAGE_URL = "https://sonoad.com/wp-content/uploads/2021/02/%EC%BB%A4%ED%94%BC-%EC%BF%A0%ED%8F%B0-%EB%A7%8C%EB%93%A4%EA%B8%B0-%EC%B9%B4%ED%8E%98%EC%BF%A0%ED%8F%B0-%EC%84%A4%EB%AA%85-1.jpg";
    private static final String SAMPLE_BACK_IMAGE_URL = "https://sonoad.com/wp-content/uploads/2021/02/%EC%BB%A4%ED%94%BC-%EC%BF%A0%ED%8F%B0-%EB%A7%8C%EB%93%A4%EA%B8%B0-%EC%B9%B4%ED%8E%98%EC%BF%A0%ED%8F%B0-%EC%84%A4%EB%AA%85-2.jpg";
    private static final String SAMPLE_STAMP_IMAGE_URL = "https://files.slack.com/files-pri/T05FM0W106M-F05FY6Y4YV6/image.png";

    private final SampleFrontImageRepository sampleFrontImageRepository;
    private final SampleBackImageRepository sampleBackImageRepository;
    private final SampleStampCoordinateRepository sampleStampCoordinateRepository;
    private final SampleStampImageRepository sampleStampImageRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void initializeSamples() {
        SampleFrontImage sampleFrontImage = new SampleFrontImage(SAMPLE_FRONT_IMAGE_URL);
        sampleFrontImageRepository.save(sampleFrontImage);

        SampleBackImage sampleBackImage = new SampleBackImage(SAMPLE_BACK_IMAGE_URL);
        sampleBackImageRepository.save(sampleBackImage);

        List<SampleStampCoordinate> sampleStampCoordinates = new ArrayList<>();

        sampleStampCoordinates.add(new SampleStampCoordinate(1, 1, 2));
        sampleStampCoordinates.add(new SampleStampCoordinate(2, 2, 2));
        sampleStampCoordinates.add(new SampleStampCoordinate(3, 3, 2));
        sampleStampCoordinates.add(new SampleStampCoordinate(4, 4, 2));
        sampleStampCoordinates.add(new SampleStampCoordinate(5, 1, 1));
        sampleStampCoordinates.add(new SampleStampCoordinate(6, 2, 1));
        sampleStampCoordinates.add(new SampleStampCoordinate(7, 3, 1));
        sampleStampCoordinates.add(new SampleStampCoordinate(8, 4, 1));

        for (SampleStampCoordinate coordinate : sampleStampCoordinates) {
            SampleStampCoordinate savedCoordinate = sampleStampCoordinateRepository.save(coordinate);
            sampleBackImage.addCoordinates(savedCoordinate);
        }

        SampleStampImage sampleStampImage = new SampleStampImage(SAMPLE_STAMP_IMAGE_URL);
        sampleStampImageRepository.save(sampleStampImage);
    }
}
