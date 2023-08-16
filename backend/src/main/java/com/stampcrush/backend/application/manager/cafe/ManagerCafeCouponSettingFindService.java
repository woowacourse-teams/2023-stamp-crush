package com.stampcrush.backend.application.manager.cafe;

import com.stampcrush.backend.application.manager.cafe.dto.CafeCouponCoordinateFindResultDto;
import com.stampcrush.backend.application.manager.cafe.dto.CafeCouponSettingFindResultDto;
import com.stampcrush.backend.entity.cafe.Cafe;
import com.stampcrush.backend.entity.cafe.CafeCouponDesign;
import com.stampcrush.backend.exception.CafeCouponSettingNotFoundException;
import com.stampcrush.backend.exception.CafeNotFoundException;
import com.stampcrush.backend.repository.cafe.CafeCouponDesignRepository;
import com.stampcrush.backend.repository.cafe.CafeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ManagerCafeCouponSettingFindService {

    private final CafeRepository cafeRepository;
    private final CafeCouponDesignRepository cafeCouponDesignRepository;

    public CafeCouponSettingFindResultDto findCafeCouponSetting(Long cafeId) {
        Cafe cafe = cafeRepository.findById(cafeId)
                .orElseThrow(() -> new CafeNotFoundException("카페를 찾지 못했습니다."));
        CafeCouponDesign cafeCouponDesign = cafeCouponDesignRepository.findByCafe(cafe)
                .orElseThrow(() -> new CafeCouponSettingNotFoundException("카페 디자인을 찾을 수 없습니다."));
        return new CafeCouponSettingFindResultDto(
                cafeCouponDesign.getFrontImageUrl(),
                cafeCouponDesign.getBackImageUrl(),
                cafeCouponDesign.getStampImageUrl(),
                cafeCouponDesign.getCafeStampCoordinates().stream()
                        .map(stamp -> new CafeCouponCoordinateFindResultDto(
                                stamp.getStampOrder(),
                                stamp.getXCoordinate(),
                                stamp.getYCoordinate())).toList());
    }
}
