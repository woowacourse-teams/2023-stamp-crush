package com.stampcrush.backend.application.cafe;

import com.stampcrush.backend.application.cafe.dto.CafeCreate;
import com.stampcrush.backend.application.cafe.dto.CafeFindResult;
import com.stampcrush.backend.entity.cafe.Cafe;
import com.stampcrush.backend.entity.cafe.CafeCouponDesign;
import com.stampcrush.backend.entity.cafe.CafePolicy;
import com.stampcrush.backend.entity.cafe.CafeStampCoordinate;
import com.stampcrush.backend.entity.sample.SampleBackImage;
import com.stampcrush.backend.entity.sample.SampleFrontImage;
import com.stampcrush.backend.entity.sample.SampleStampCoordinate;
import com.stampcrush.backend.entity.sample.SampleStampImage;
import com.stampcrush.backend.entity.user.Owner;
import com.stampcrush.backend.repository.cafe.CafeCouponDesignRepository;
import com.stampcrush.backend.repository.cafe.CafePolicyRepository;
import com.stampcrush.backend.repository.cafe.CafeRepository;
import com.stampcrush.backend.repository.cafe.CafeStampCoordinateRepository;
import com.stampcrush.backend.repository.sample.SampleBackImageRepository;
import com.stampcrush.backend.repository.sample.SampleFrontImageRepository;
import com.stampcrush.backend.repository.sample.SampleStampCoordinateRepository;
import com.stampcrush.backend.repository.sample.SampleStampImageRepository;
import com.stampcrush.backend.repository.user.OwnerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class CafeService {

    private final CafeRepository cafeRepository;
    private final OwnerRepository ownerRepository;
    private final SampleFrontImageRepository sampleFrontImageRepository;
    private final SampleBackImageRepository sampleBackImageRepository;
    private final SampleStampImageRepository sampleStampImageRepository;
    private final CafePolicyRepository cafePolicyRepository;
    private final CafeCouponDesignRepository cafeCouponDesignRepository;
    private final CafeStampCoordinateRepository cafeStampCoordinateRepository;

    public Long createCafe(CafeCreate cafeCreate) {
        Owner owner = ownerRepository.findById(cafeCreate.getOwnerId())
                .orElseThrow(() -> new IllegalArgumentException("회원가입을 먼저 진행해주세요."));
        Cafe cafe = new Cafe(
                cafeCreate.getName(),
                cafeCreate.getOpenTime(),
                cafeCreate.getCloseTime(),
                cafeCreate.getTelephoneNumber(),
                cafeCreate.getCafeImageUrl(),
                cafeCreate.getRoadAddress(),
                cafeCreate.getDetailAddress(),
                cafeCreate.getBusinessRegistrationNumber(),
                owner);
        cafeRepository.save(cafe);
        cafePolicyRepository.save(CafePolicy.createDefaultCafePolicy(cafe));

        SampleFrontImage defaultSampleFrontImage = findDefaultSampleFrontImage();
        SampleBackImage defaultSampleBackImage = findDefaultSampleBackImage();
        SampleStampImage defaultSampleStampImage = findDefaultSampleStampImage();
        CafeCouponDesign defaultCafeCouponDesign = new CafeCouponDesign(
                defaultSampleFrontImage.getImageUrl(),
                defaultSampleBackImage.getImageUrl(),
                defaultSampleStampImage.getImageUrl(),
                false,
                cafe);
        cafeCouponDesignRepository.save(defaultCafeCouponDesign);
        for (SampleStampCoordinate sampleStampCoordinate : defaultSampleBackImage.getSampleStampCoordinates()) {
            CafeStampCoordinate cafeStampCoordinate = new CafeStampCoordinate(sampleStampCoordinate.getStampOrder(),
                    sampleStampCoordinate.getXCoordinate(),
                    sampleStampCoordinate.getYCoordinate(),
                    defaultCafeCouponDesign);
            cafeStampCoordinateRepository.save(cafeStampCoordinate);
        }
        return cafe.getId();
    }

    private SampleFrontImage findDefaultSampleFrontImage() {
        List<SampleFrontImage> sampleFrontImages = sampleFrontImageRepository.findAll();
        if (sampleFrontImages.isEmpty()) {
            throw new IllegalArgumentException("저장된 샘플 앞면 이미지가 없습니다.");
        }
        return sampleFrontImages.get(0);
    }

    private SampleBackImage findDefaultSampleBackImage() {
        List<SampleBackImage> sampleBackImages = sampleBackImageRepository.findAll();
        if (sampleBackImages.isEmpty()) {
            throw new IllegalArgumentException("저장된 샘플 뒷면 이미지가 없습니다.");
        }
        return sampleBackImages.stream()
                .filter(sampleBackImage -> sampleBackImage.getSampleStampCoordinates().size() == 10)
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("최대 스탬프 개수 10개에 해당하는 샘플 뒷면 이미지가 없습니다."));
    }

    private SampleStampImage findDefaultSampleStampImage() {
        List<SampleStampImage> sampleStampImages = sampleStampImageRepository.findAll();
        if (sampleStampImages.isEmpty()) {
            throw new IllegalArgumentException("저장된 샘플 스탬프 이미지가 없습니다.");
        }
        return sampleStampImages.get(0);
    }

    @Transactional(readOnly = true)
    public List<CafeFindResult> findAllCafes(Long ownerId) {
        List<Cafe> cafes = cafeRepository.findAllByOwnerId(ownerId);
        return cafes.stream()
                .map(CafeFindResult::from)
                .toList();
    }
}
