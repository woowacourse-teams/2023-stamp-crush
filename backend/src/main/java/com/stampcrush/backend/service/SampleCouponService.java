package com.stampcrush.backend.service;

import com.stampcrush.backend.entity.sample.SampleBackImage;
import com.stampcrush.backend.entity.sample.SampleFrontImage;
import com.stampcrush.backend.entity.sample.SampleStampCoordinate;
import com.stampcrush.backend.entity.sample.SampleStampImage;
import com.stampcrush.backend.repository.sample.SampleBackImageRepository;
import com.stampcrush.backend.repository.sample.SampleFrontImageRepository;
import com.stampcrush.backend.repository.sample.SampleStampCoordinateRepository;
import com.stampcrush.backend.repository.sample.SampleStampImageRepository;
import com.stampcrush.backend.service.dto.SampleCouponsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SampleCouponService {

    private final SampleFrontImageRepository sampleFrontImageRepository;
    private final SampleBackImageRepository sampleBackImageRepository;
    private final SampleStampCoordinateRepository sampleStampCoordinateRepository;
    private final SampleStampImageRepository sampleStampImageRepository;

    public SampleCouponsDto findSampleCouponsBy(Integer maxStampCount) {
        List<SampleFrontImage> sampleFrontImages = sampleFrontImageRepository.findAll();
        List<SampleStampImage> sampleStampImages = sampleStampImageRepository.findAll();

        if (maxStampCount == null) {
            List<SampleStampCoordinate> sampleStampCoordinates = sampleStampCoordinateRepository.findAll();
            List<SampleBackImage> sampleBackImages = sampleBackImageRepository.findAll();
            return new SampleCouponsDto(sampleFrontImages, sampleBackImages, sampleStampCoordinates, sampleStampImages);
        }

        List<SampleBackImage> sampleBackImages = sampleBackImageRepository.findAll()
                .stream()
                .filter(isSameMaxStampCount(maxStampCount))
                .collect(Collectors.toList());

        List<SampleStampCoordinate> coordinates = new ArrayList<>();
        for (SampleBackImage sampleBackImage : sampleBackImages) {
            coordinates.addAll(sampleStampCoordinateRepository.findSampleStampCoordinateBySampleBackImageId(sampleBackImage.getId()));
        }

        return new SampleCouponsDto(sampleFrontImages, sampleBackImages, coordinates, sampleStampImages);
    }

    private Predicate<SampleBackImage> isSameMaxStampCount(int maxStampCount) {
        return sampleBackImage -> {
            List<SampleStampCoordinate> coordinates = sampleStampCoordinateRepository.findSampleStampCoordinateBySampleBackImageId(sampleBackImage.getId());
            return coordinates.size() == maxStampCount;
        };
    }
}
