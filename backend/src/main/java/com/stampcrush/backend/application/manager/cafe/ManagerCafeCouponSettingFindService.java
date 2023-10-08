package com.stampcrush.backend.application.manager.cafe;

import com.stampcrush.backend.application.manager.cafe.dto.CafeCouponCoordinateFindResultDto;
import com.stampcrush.backend.application.manager.cafe.dto.CafeCouponSettingFindResultDto;
import com.stampcrush.backend.entity.cafe.Cafe;
import com.stampcrush.backend.entity.cafe.CafeCouponDesign;
import com.stampcrush.backend.entity.coupon.Coupon;
import com.stampcrush.backend.entity.user.Owner;
import com.stampcrush.backend.exception.CafeCouponSettingNotFoundException;
import com.stampcrush.backend.exception.CafeNotFoundException;
import com.stampcrush.backend.exception.CouponNotFoundException;
import com.stampcrush.backend.exception.OwnerNotFoundException;
import com.stampcrush.backend.repository.cafe.CafeCouponDesignRepository;
import com.stampcrush.backend.repository.cafe.CafeRepository;
import com.stampcrush.backend.repository.coupon.CouponRepository;
import com.stampcrush.backend.repository.user.OwnerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
@Service
public class ManagerCafeCouponSettingFindService {

    private final CafeRepository cafeRepository;
    private final CouponRepository couponRepository;
    private final CafeCouponDesignRepository cafeCouponDesignRepository;
    private final OwnerRepository ownerRepository;

    public CafeCouponSettingFindResultDto findCafeCouponSetting(Long ownerId, Long cafeId) {
        Cafe cafe = cafeRepository.findById(cafeId)
                .orElseThrow(() -> new CafeNotFoundException("카페를 찾지 못했습니다."));
        Owner owner = ownerRepository.findById(ownerId)
                .orElseThrow(() -> new OwnerNotFoundException("사장을 찾지 못했습니다."));
        cafe.validateOwnership(owner);
        CafeCouponDesign cafeCouponDesign = cafeCouponDesignRepository.findByCafeAndDeletedIsFalse(cafe)
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

    public CafeCouponSettingFindResultDto findCouponSetting(Long ownerId, Long cafeId, Long couponId) {
        Cafe cafe = cafeRepository.findById(cafeId)
                .orElseThrow(() -> new CafeNotFoundException("카페를 찾지 못했습니다."));
        Owner owner = ownerRepository.findById(ownerId)
                .orElseThrow(() -> new OwnerNotFoundException("사장을 찾지 못했습니다."));
        cafe.validateOwnership(owner);
        Coupon coupon = couponRepository.findById(couponId)
                .orElseThrow(() -> new CouponNotFoundException("couponId: " + couponId + " 쿠폰을 찾지 못했습니다."));
        CafeCouponDesign couponDesign = coupon.getCafeCouponDesign();
        return CafeCouponSettingFindResultDto.from(couponDesign);
    }
}
