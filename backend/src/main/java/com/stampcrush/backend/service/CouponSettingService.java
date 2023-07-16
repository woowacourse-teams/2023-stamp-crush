package com.stampcrush.backend.service;

import com.stampcrush.backend.entity.cafe.Cafe;
import com.stampcrush.backend.entity.cafe.CafeCouponDesign;
import com.stampcrush.backend.entity.cafe.CafePolicy;
import com.stampcrush.backend.entity.cafe.CafeStampCoordinate;
import com.stampcrush.backend.repository.cafe.CafeCouponDesignRepository;
import com.stampcrush.backend.repository.cafe.CafePolicyRepository;
import com.stampcrush.backend.repository.cafe.CafeRepository;
import com.stampcrush.backend.repository.cafe.CafeStampCoordinateRepository;
import com.stampcrush.backend.service.dto.CouponSettingDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CouponSettingService {

    private final CafeRepository cafeRepository;
    private final CafeCouponDesignRepository cafeCouponDesignRepository;
    private final CafePolicyRepository cafePolicyRepository;
    private final CafeStampCoordinateRepository cafeStampCoordinateRepository;

    @Transactional
    public void modifyCouponSettings(Long cafeId, CouponSettingDto couponSettingDto) {
        Optional<Cafe> findCafe = cafeRepository.findById(cafeId);

        if (findCafe.isEmpty()) {
            throw new IllegalArgumentException("존재하지 않는 카페입니다.");
        }

        Cafe cafe = findCafe.get();

        softDeletePreviousSettings(cafe);
        insertNewSettings(cafe, couponSettingDto);
    }

    private void softDeletePreviousSettings(Cafe cafe) {
        softDeleteCafePolicy(cafe);
        softDeleteCafeCouponDesign(cafe);
    }

    private void softDeleteCafePolicy(Cafe cafe) {
        Optional<CafePolicy> findCafePolicy = cafePolicyRepository.findByCafeAndDeletedIsFalse(cafe);
        if (findCafePolicy.isPresent()) {
            CafePolicy currentCafePolicy = findCafePolicy.get();
            currentCafePolicy.softDelete();
        }
    }

    private void softDeleteCafeCouponDesign(Cafe cafe) {
        Optional<CafeCouponDesign> findCafeCouponDesign = cafeCouponDesignRepository.findByCafeAndDeletedIsFalse(cafe);
        if (findCafeCouponDesign.isPresent()) {
            CafeCouponDesign currentCafeCouponDesign = findCafeCouponDesign.get();
            currentCafeCouponDesign.softDelete();
        }
    }

    private void insertNewSettings(Cafe cafe, CouponSettingDto couponSettingDto) {
        insertNewCafePolicy(cafe, couponSettingDto);
        insertNewCafeCouponDesign(cafe, couponSettingDto);
    }

    private void insertNewCafePolicy(Cafe cafe, CouponSettingDto couponSettingDto) {
        CouponSettingDto.CafePolicyDto cafePolicyDto = couponSettingDto.getCafePolicyDto();
        CafePolicy newCafePolicy = new CafePolicy(
                cafePolicyDto.getMaxStampCount(),
                cafePolicyDto.getReward(),
                cafePolicyDto.getExpirePeriod(),
                false,
                cafe
        );
        CafePolicy savedCafePolicy = cafePolicyRepository.save(newCafePolicy);
    }

    private void insertNewCafeCouponDesign(Cafe cafe, CouponSettingDto couponSettingDto) {
        CouponSettingDto.CafeCouponDesignDto cafeCouponDesignDto = couponSettingDto.getCafeCouponDesignDto();
        CafeCouponDesign newCafeCouponDesign = new CafeCouponDesign(
                cafeCouponDesignDto.getFrontImageUrl(),
                cafeCouponDesignDto.getBackImageUrl(),
                cafeCouponDesignDto.getStampImageUrl(),
                false,
                cafe
        );
        CafeCouponDesign savedCafeCouponDesign = cafeCouponDesignRepository.save(newCafeCouponDesign);
        insertNewCafeStampCoordinates(savedCafeCouponDesign, cafeCouponDesignDto.getCoordinates());
    }

    private void insertNewCafeStampCoordinates(
            CafeCouponDesign savedCafeCouponDesign,
            List<CouponSettingDto.CafeCouponDesignDto.CafeStampCoordinateDto> coordinates
    ) {
        for (CouponSettingDto.CafeCouponDesignDto.CafeStampCoordinateDto coordinate : coordinates) {
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
