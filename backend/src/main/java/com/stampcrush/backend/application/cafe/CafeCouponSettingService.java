package com.stampcrush.backend.application.cafe;

import com.stampcrush.backend.application.cafe.dto.CafeCouponSettingDto;
import com.stampcrush.backend.entity.cafe.Cafe;
import com.stampcrush.backend.entity.cafe.CafeCouponDesign;
import com.stampcrush.backend.entity.cafe.CafePolicy;
import com.stampcrush.backend.entity.cafe.CafeStampCoordinate;
import com.stampcrush.backend.repository.cafe.CafeCouponDesignRepository;
import com.stampcrush.backend.repository.cafe.CafePolicyRepository;
import com.stampcrush.backend.repository.cafe.CafeRepository;
import com.stampcrush.backend.repository.cafe.CafeStampCoordinateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CafeCouponSettingService {

    private final CafeRepository cafeRepository;
    private final CafeCouponDesignRepository cafeCouponDesignRepository;
    private final CafePolicyRepository cafePolicyRepository;
    private final CafeStampCoordinateRepository cafeStampCoordinateRepository;

    @Transactional
    public void updateCafeCouponSetting(Long cafeId, CafeCouponSettingDto cafeCouponSettingDto) {
        Optional<Cafe> findCafe = cafeRepository.findById(cafeId);

        if (findCafe.isEmpty()) {
            throw new IllegalArgumentException("존재하지 않는 카페입니다.");
        }

        Cafe cafe = findCafe.get();

        deletePreviousSetting(cafe);
        createNewSetting(cafe, cafeCouponSettingDto);
    }

    private void deletePreviousSetting(Cafe cafe) {
        deleteCafePolicy(cafe);
        deleteCafeCouponDesign(cafe);
    }

    private void deleteCafePolicy(Cafe cafe) {
        Optional<CafePolicy> findCafePolicy = cafePolicyRepository.findByCafeAndDeletedIsFalse(cafe);
        if (findCafePolicy.isPresent()) {
            CafePolicy currentCafePolicy = findCafePolicy.get();
            cafePolicyRepository.delete(currentCafePolicy);
        }
    }

    private void deleteCafeCouponDesign(Cafe cafe) {
        Optional<CafeCouponDesign> findCafeCouponDesign = cafeCouponDesignRepository.findByCafeAndDeletedIsFalse(cafe);
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
        createNewCafeStampCoordinates(savedCafeCouponDesign, cafeCouponDesignDto.getCoordinates());
    }

    private void createNewCafeStampCoordinates(
            CafeCouponDesign savedCafeCouponDesign,
            List<CafeCouponSettingDto.CafeCouponDesignDto.CafeStampCoordinateDto> coordinates
    ) {
        for (CafeCouponSettingDto.CafeCouponDesignDto.CafeStampCoordinateDto coordinate : coordinates) {
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
