package com.stampcrush.backend.application.manager.sample;

import com.stampcrush.backend.application.manager.sample.dto.SampleCouponsFindResultDto;
import com.stampcrush.backend.entity.sample.SampleBackImage;
import com.stampcrush.backend.entity.sample.SampleFrontImage;
import com.stampcrush.backend.entity.sample.SampleStampCoordinate;
import com.stampcrush.backend.entity.sample.SampleStampImage;
import com.stampcrush.backend.repository.sample.SampleBackImageRepository;
import com.stampcrush.backend.repository.sample.SampleFrontImageRepository;
import com.stampcrush.backend.repository.sample.SampleStampCoordinateRepository;
import com.stampcrush.backend.repository.sample.SampleStampImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ManagerSampleCouponFindService {

    private final SampleFrontImageRepository sampleFrontImageRepository;
    private final SampleBackImageRepository sampleBackImageRepository;
    private final SampleStampCoordinateRepository sampleStampCoordinateRepository;
    private final SampleStampImageRepository sampleStampImageRepository;

    public SampleCouponsFindResultDto findSampleCouponsByMaxStampCount(Integer maxStampCount) {
        List<SampleFrontImage> sampleFrontImages = sampleFrontImageRepository.findAll();
        List<SampleStampImage> sampleStampImages = sampleStampImageRepository.findAll();

        if (maxStampCount == null) {
            List<SampleStampCoordinate> sampleStampCoordinates = sampleStampCoordinateRepository.findAll();
            List<SampleBackImage> sampleBackImages = sampleBackImageRepository.findAll();
            return SampleCouponsFindResultDto.of(sampleFrontImages, sampleBackImages, sampleStampCoordinates, sampleStampImages);
        }

        List<SampleBackImage> sampleBackImages = sampleBackImageRepository.findAll()
                .stream()
                .filter(isSameMaxStampCount(maxStampCount))
                .toList();

        List<SampleStampCoordinate> coordinates = new ArrayList<>();
        for (SampleBackImage sampleBackImage : sampleBackImages) {
            coordinates.addAll(sampleStampCoordinateRepository.findSampleStampCoordinateBySampleBackImage(sampleBackImage));
        }

        return SampleCouponsFindResultDto.of(sampleFrontImages, sampleBackImages, coordinates, sampleStampImages);
    }

    private Predicate<SampleBackImage> isSameMaxStampCount(int maxStampCount) {
        return sampleBackImage -> {
            List<SampleStampCoordinate> coordinates = sampleStampCoordinateRepository.findSampleStampCoordinateBySampleBackImage(sampleBackImage);
            return coordinates.size() == maxStampCount;
        };
    }
}
