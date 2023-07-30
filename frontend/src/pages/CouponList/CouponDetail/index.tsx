import { MouseEvent, useState } from 'react';
import { CafeType, CouponType } from '..';
import Text from '../../../components/Text';
import {
  CafeImage,
  CloseButton,
  ContentContainer,
  CouponDetailContainer,
  OverviewContainer,
} from './style';
import { BiArrowBack } from 'react-icons/bi';
import { FaRegClock, FaPhoneAlt } from 'react-icons/fa';
import { FaLocationDot } from 'react-icons/fa6';
import FlippedCoupon from '../FlippedCoupon';

interface CouponDetailProps {
  isDetail: boolean;
  isShown: boolean;
  coupon: CouponType;
  cafe: CafeType;
  closeDetail: (e: MouseEvent<HTMLButtonElement>) => void;
}

// TODO:이후 카페 관리 병합 후 parseUtil 사용
const CouponDetail = ({ isDetail, isShown, coupon, cafe, closeDetail }: CouponDetailProps) => {
  return (
    <CouponDetailContainer $isDetail={isDetail}>
      <CafeImage src={cafe.cafe.cafeImageUrl} />
      <FlippedCoupon
        frontImageUrl={coupon.couponInfos[0].frontImageUrl}
        backImageUrl={coupon.couponInfos[0].backImageUrl}
        isShown={isShown}
        coordinates={coupon.couponInfos[0].coordinates}
      />
      <CloseButton onClick={closeDetail}>
        <BiArrowBack size={24} />
      </CloseButton>
      <OverviewContainer>
        <Text variant="subTitle">{coupon.cafeInfo.name}</Text>
        <Text>{cafe.cafe.introduction}</Text>
      </OverviewContainer>
      <ContentContainer>
        <Text>
          <FaRegClock size={24} />
          {`여는 시간 ${cafe.cafe.openTime}\n닫는 시간 ${cafe.cafe.closeTime}`}
        </Text>
        <Text>
          <FaPhoneAlt size={24} />
          {cafe.cafe.telephoneNumber}
        </Text>
        <Text>
          <FaLocationDot size={24} />
          {cafe.cafe.roadAddress + ' ' + cafe.cafe.detailAddress}
        </Text>
      </ContentContainer>
    </CouponDetailContainer>
  );
};

export default CouponDetail;
