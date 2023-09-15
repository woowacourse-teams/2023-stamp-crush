package com.stampcrush.backend.application.manager.cafe;

import com.stampcrush.backend.application.manager.cafe.dto.CafeCouponSettingDto;
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
import com.stampcrush.backend.repository.user.OwnerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional
@Service
public class ManagerCafeCouponSettingCommandService {

    private final CafeRepository cafeRepository;
    private final CafeCouponDesignRepository cafeCouponDesignRepository;
    private final CafePolicyRepository cafePolicyRepository;
    private final CafeStampCoordinateRepository cafeStampCoordinateRepository;
    private final OwnerRepository ownerRepository;

    public void updateCafeCouponSetting(Long ownerId, Long cafeId, CafeCouponSettingDto cafeCouponSettingDto) {
        Cafe cafe = findExistingCafe(cafeId);
        Owner owner = ownerRepository.findById(ownerId)
                .orElseThrow(() -> new OwnerNotFoundException("사장이 없습니다."));
        cafe.validateOwnership(owner);
        deletePreviousSetting(cafe);
        createNewSetting(cafe, cafeCouponSettingDto);
    }

    private Cafe findExistingCafe(Long cafeId) {
        Optional<Cafe> findCafe = cafeRepository.findById(cafeId);

        if (findCafe.isEmpty()) {
            throw new CafeNotFoundException("존재하지 않는 카페입니다.");
        }

        return findCafe.get();
    }

    private void deletePreviousSetting(Cafe cafe) {
        deleteCafePolicy(cafe);
        deleteCafeCouponDesign(cafe);
    }

    private void deleteCafePolicy(Cafe cafe) {
        Optional<CafePolicy> findCafePolicy = cafePolicyRepository.findByCafe(cafe);
        if (findCafePolicy.isPresent()) {
            CafePolicy currentCafePolicy = findCafePolicy.get();
            cafePolicyRepository.delete(currentCafePolicy);
        }
    }

    private void deleteCafeCouponDesign(Cafe cafe) {
        Optional<CafeCouponDesign> findCafeCouponDesign = cafeCouponDesignRepository.findByCafe(cafe);
        if (findCafeCouponDesign.isPresent()) {
            CafeCouponDesign currentCafeCouponDesign = findCafeCouponDesign.get();
            cafeCouponDesignRepository.delete(currentCafeCouponDesign);
        }
    }

    private void createNewSetting(Cafe cafe, CafeCouponSettingDto cafeCouponSettingDto) {
        createNewCafePolicy(cafe, cafeCouponSettingDto);
        createNewCafeCouponDesign(cafe, cafeCouponSettingDto);
    }

    private void createNewCafePolicy(Cafe cafe, CafeCouponSettingDto cafeCouponSettingDto) {
        CafeCouponSettingDto.CafePolicyDto cafePolicyDto = cafeCouponSettingDto.getCafePolicyDto();
        CafePolicy newCafePolicy = new CafePolicy(
                cafePolicyDto.getMaxStampCount(),
                cafePolicyDto.getReward(),
                cafePolicyDto.getExpirePeriod(),
                false,
                cafe
        );
        CafePolicy savedCafePolicy = cafePolicyRepository.save(newCafePolicy);
    }

    private void createNewCafeCouponDesign(Cafe cafe, CafeCouponSettingDto cafeCouponSettingDto) {
        CafeCouponSettingDto.CafeCouponDesignDto cafeCouponDesignDto = cafeCouponSettingDto.getCafeCouponDesignDto();
        CafeCouponDesign newCafeCouponDesign = new CafeCouponDesign(
                cafeCouponDesignDto.getFrontImageUrl(),
                cafeCouponDesignDto.getBackImageUrl(),
                cafeCouponDesignDto.getStampImageUrl(),
                false,
                cafe
        );
        CafeCouponDesign savedCafeCouponDesign = cafeCouponDesignRepository.save(newCafeCouponDesign);
        for (CafeCouponSettingDto.CafeCouponDesignDto.CafeStampCoordinateDto coordinate : cafeCouponDesignDto.getCoordinates()) {
            CafeStampCoordinate newCafeStampCoordinate = new CafeStampCoordinate(
                    coordinate.getOrder(),
                    coordinate.getXCoordinate(),
                    coordinate.getYCoordinate(),
                    savedCafeCouponDesign
            );
            CafeStampCoordinate savedCafeStampCoordinate = cafeStampCoordinateRepository.save(newCafeStampCoordinate);
        }
    }
}
