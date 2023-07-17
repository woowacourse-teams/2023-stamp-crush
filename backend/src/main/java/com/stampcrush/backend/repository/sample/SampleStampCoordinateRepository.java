package com.stampcrush.backend.repository.sample;

import com.stampcrush.backend.entity.sample.SampleBackImage;
import com.stampcrush.backend.entity.sample.SampleStampCoordinate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SampleStampCoordinateRepository extends JpaRepository<SampleStampCoordinate, Long> {

    List<SampleStampCoordinate> findSampleStampCoordinateBySampleBackImageId(Long sampleBackImageId);

    List<SampleStampCoordinate> findSampleStampCoordinateBySampleBackImage(SampleBackImage sampleBackImage);
}
