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
        CafeCouponDesign defaultCafeCouponDesign = cafeCouponDesignRepository.save(new CafeCouponDesign(
                SampleImages.FRONT_IMAGE_URL,
                SampleImages.BACK_IMAGE_URL,
                SampleImages.STAMP_IMAGE_URL,
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

    public void updateCafeInfo(Long ownerId, CafeUpdateDto cafeUpdateDto, Long cafeId) {
        Cafe cafe = cafeRepository.findById(cafeId)
                .orElseThrow(() -> new CafeNotFoundException("존재하지 않는 카페 입니다."));
        Owner owner = ownerRepository.findById(ownerId)
                .orElseThrow(() -> new OwnerNotFoundException("존재하지 않는 사장입니다."));
        cafe.validateOwnership(owner);
        cafe.updateCafeAdditionalInformation(
                cafeUpdateDto.getIntroduction(),
                cafeUpdateDto.getOpenTime(),
                cafeUpdateDto.getCloseTime(),
                cafeUpdateDto.getTelephoneNumber(),
                cafeUpdateDto.getCafeImageUrl()
        );
    }
}
