package com.stampcrush.backend.application.cafe;

import com.stampcrush.backend.application.cafe.dto.CafeCreateDto;
import com.stampcrush.backend.application.cafe.dto.CafeFindResultDto;
import com.stampcrush.backend.application.cafe.dto.CafeUpdateDto;
import com.stampcrush.backend.entity.cafe.Cafe;
import com.stampcrush.backend.entity.cafe.CafeCouponDesign;
import com.stampcrush.backend.entity.cafe.CafePolicy;
import com.stampcrush.backend.entity.cafe.CafeStampCoordinate;
import com.stampcrush.backend.entity.sample.SampleBackImage;
import com.stampcrush.backend.entity.sample.SampleFrontImage;
import com.stampcrush.backend.entity.sample.SampleStampCoordinate;
import com.stampcrush.backend.entity.sample.SampleStampImage;
import com.stampcrush.backend.entity.user.Owner;
import com.stampcrush.backend.exception.CafeNotFoundException;
import com.stampcrush.backend.exception.OwnerNotFoundException;
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

import static com.stampcrush.backend.api.cafe.SampleCouponImage.*;

@RequiredArgsConstructor
@Transactional
@Service
public class CafeService {

    private final CafeRepository cafeRepository;
    private final OwnerRepository ownerRepository;
    private final SampleFrontImageRepository sampleFrontImageRepository;
    private final SampleBackImageRepository sampleBackImageRepository;
    private final SampleStampImageRepository sampleStampImageRepository;
    private final SampleStampCoordinateRepository sampleStampCoordinateRepository;
    private final CafePolicyRepository cafePolicyRepository;
    private final CafeCouponDesignRepository cafeCouponDesignRepository;
    private final CafeStampCoordinateRepository cafeStampCoordinateRepository;

    public Long createCafe(CafeCreateDto cafeCreateDto) {
        Owner owner = findExistingOwnerById(cafeCreateDto.getOwnerId());
        Cafe savedCafe = saveCafe(cafeCreateDto, owner);
        assignDefaultCafePolicyToCafe(savedCafe);
        assignDefaultSampleCafeCouponToCafe(savedCafe);

        return savedCafe.getId();
    }

    private Owner findExistingOwnerById(Long ownerId) {
        return ownerRepository.findById(ownerId)
                .orElseThrow(() -> new OwnerNotFoundException("회원가입을 먼저 진행해주세요."));
    }

    private Cafe saveCafe(CafeCreateDto cafeCreateDto, Owner owner) {
        return cafeRepository.save(
                new Cafe(
                        cafeCreateDto.getName(),
                        cafeCreateDto.getRoadAddress(),
                        cafeCreateDto.getDetailAddress(),
                        cafeCreateDto.getBusinessRegistrationNumber(),
                        owner
                )
        );
    }

    private void assignDefaultCafePolicyToCafe(Cafe savedCafe) {
        CafePolicy defaultCafePolicy = CafePolicy.createDefaultCafePolicy(savedCafe);
        cafePolicyRepository.save(defaultCafePolicy);
    }

    private void assignDefaultSampleCafeCouponToCafe(Cafe savedCafe) {
        SampleFrontImage defaultSampleFrontImage = sampleFrontImageRepository.save(SAMPLE_FRONT_IMAGE);
        SampleBackImage defaultSampleBackImage = sampleBackImageRepository.save(SAMPLE_BACK_IMAGE);
        sampleStampCoordinateRepository.save(new SampleStampCoordinate(1, 100, 100, defaultSampleBackImage));
        SampleStampImage defaultSampleStampImage = sampleStampImageRepository.save(SAMPLE_STAMP_IMAGE);
        CafeCouponDesign defaultCafeCouponDesign = new CafeCouponDesign(
                defaultSampleFrontImage.getImageUrl(),
                defaultSampleBackImage.getImageUrl(),
                defaultSampleStampImage.getImageUrl(),
                false,
                savedCafe
        );
        cafeCouponDesignRepository.save(defaultCafeCouponDesign);
        List<SampleStampCoordinate> sampleStampCoordinates = sampleStampCoordinateRepository.findSampleStampCoordinateBySampleBackImage(defaultSampleBackImage);
        for (SampleStampCoordinate sampleStampCoordinate : sampleStampCoordinates) {
            CafeStampCoordinate cafeStampCoordinate = new CafeStampCoordinate(sampleStampCoordinate.getStampOrder(),
                    sampleStampCoordinate.getXCoordinate(),
                    sampleStampCoordinate.getYCoordinate(),
                    defaultCafeCouponDesign
            );
            cafeStampCoordinateRepository.save(cafeStampCoordinate);
        }
    }

    @Transactional(readOnly = true)
    public List<CafeFindResultDto> findCafesByOwner(Long ownerId) {
        List<Cafe> cafes = cafeRepository.findAllByOwnerId(ownerId);
        return cafes.stream()
                .map(CafeFindResultDto::from)
                .toList();
    }

    public void updateCafeInfo(CafeUpdateDto cafeUpdateDto, Long cafeId) {
        Cafe cafe = cafeRepository.findById(cafeId)
                .orElseThrow(() -> new CafeNotFoundException("존재하지 않는 카페 입니다."));
        cafe.updateCafeAdditionalInformation(
                cafeUpdateDto.getOpenTime(),
                cafeUpdateDto.getCloseTime(),
                cafeUpdateDto.getTelephoneNumber(),
                cafeUpdateDto.getCafeImageUrl()
        );
    }
}
