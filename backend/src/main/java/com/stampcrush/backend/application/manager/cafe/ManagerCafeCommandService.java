package com.stampcrush.backend.application.manager.cafe;

import com.stampcrush.backend.application.manager.cafe.dto.CafeCreateDto;
import com.stampcrush.backend.application.manager.cafe.dto.CafeUpdateDto;
import com.stampcrush.backend.entity.cafe.Cafe;
import com.stampcrush.backend.entity.cafe.CafeCouponDesign;
import com.stampcrush.backend.entity.cafe.CafePolicy;
import com.stampcrush.backend.entity.cafe.CafeStampCoordinate;
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

@RequiredArgsConstructor
@Transactional
@Service
public class ManagerCafeCommandService {

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
//        SampleFrontImage defaultSampleFrontImage = sampleFrontImageRepository.save(SAMPLE_FRONT_IMAGE);
//        SampleBackImage defaultSampleBackImage = sampleBackImageRepository.save(SAMPLE_BACK_IMAGE);
//        SampleStampImage defaultSampleStampImage = sampleStampImageRepository.save(SAMPLE_STAMP_IMAGE);

//        sampleStampCoordinateRepository.save(new SampleStampCoordinate(1, 37, 50, defaultSampleBackImage));
//        sampleStampCoordinateRepository.save(new SampleStampCoordinate(2, 86, 50, defaultSampleBackImage));
//        sampleStampCoordinateRepository.save(new SampleStampCoordinate(3, 134, 50, defaultSampleBackImage));
//        sampleStampCoordinateRepository.save(new SampleStampCoordinate(4, 182, 50, defaultSampleBackImage));
//        sampleStampCoordinateRepository.save(new SampleStampCoordinate(5, 233, 50, defaultSampleBackImage));
//        sampleStampCoordinateRepository.save(new SampleStampCoordinate(6, 37, 100, defaultSampleBackImage));
//        sampleStampCoordinateRepository.save(new SampleStampCoordinate(7, 86, 100, defaultSampleBackImage));
//        sampleStampCoordinateRepository.save(new SampleStampCoordinate(8, 134, 100, defaultSampleBackImage));
//        sampleStampCoordinateRepository.save(new SampleStampCoordinate(9, 182, 100, defaultSampleBackImage));
//        sampleStampCoordinateRepository.save(new SampleStampCoordinate(10, 233, 100, defaultSampleBackImage));

        CafeCouponDesign defaultCafeCouponDesign = cafeCouponDesignRepository.save(new CafeCouponDesign(
                "https://dnv0gl9rzyzod.cloudfront.net/prod/2023-08-17-08-50-06-263937.png",
                "https://dnv0gl9rzyzod.cloudfront.net/prod/2023-08-17-08-29-33-133005.png",
                "https://dnv0gl9rzyzod.cloudfront.net/prod/2023-08-17-08-54-58-672655.png",
                false,
                savedCafe
        ));

        cafeStampCoordinateRepository.save(new CafeStampCoordinate(1, 37, 50, defaultCafeCouponDesign));
        cafeStampCoordinateRepository.save(new CafeStampCoordinate(2, 86, 50, defaultCafeCouponDesign));
        cafeStampCoordinateRepository.save(new CafeStampCoordinate(3, 134, 50, defaultCafeCouponDesign));
        cafeStampCoordinateRepository.save(new CafeStampCoordinate(4, 182, 50, defaultCafeCouponDesign));
        cafeStampCoordinateRepository.save(new CafeStampCoordinate(5, 233, 50, defaultCafeCouponDesign));
        cafeStampCoordinateRepository.save(new CafeStampCoordinate(6, 37, 100, defaultCafeCouponDesign));
        cafeStampCoordinateRepository.save(new CafeStampCoordinate(7, 86, 100, defaultCafeCouponDesign));
        cafeStampCoordinateRepository.save(new CafeStampCoordinate(8, 134, 100, defaultCafeCouponDesign));
        cafeStampCoordinateRepository.save(new CafeStampCoordinate(9, 182, 100, defaultCafeCouponDesign));
        cafeStampCoordinateRepository.save(new CafeStampCoordinate(10, 233, 100, defaultCafeCouponDesign));
    }

    public void updateCafeInfo(CafeUpdateDto cafeUpdateDto, Long cafeId) {
        Cafe cafe = cafeRepository.findById(cafeId)
                .orElseThrow(() -> new CafeNotFoundException("존재하지 않는 카페 입니다."));
        cafe.updateCafeAdditionalInformation(
                cafeUpdateDto.getIntroduction(),
                cafeUpdateDto.getOpenTime(),
                cafeUpdateDto.getCloseTime(),
                cafeUpdateDto.getTelephoneNumber(),
                cafeUpdateDto.getCafeImageUrl()
        );
    }
}
