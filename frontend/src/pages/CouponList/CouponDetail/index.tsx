import { MouseEvent } from 'react';
import FlippedCoupon from '../FlippedCoupon';
import Text from '../../../components/Text';
import {
  CafeImage,
  CloseButton,
  ContentContainer,
  CouponDetailContainer,
  OverviewContainer,
} from './style';
import { BiArrowBack } from 'react-icons/bi';
import { FaRegClock, FaPhoneAlt, FaRegBell } from 'react-icons/fa';
import { FaLocationDot } from 'react-icons/fa6';
import { Cafe, Coupon } from '../../../types';

interface CouponDetailProps {
  isDetail: boolean;
  isShown: boolean;
  coupon: Coupon;
  cafe: Cafe;
  closeDetail: (e: MouseEvent<HTMLButtonElement>) => void;
}

const CouponDetail = ({ isDetail, isShown, coupon, cafe, closeDetail }: CouponDetailProps) => {
  // FIXME: cafeName 등 넘겨받은 데이터 이후에 알맞게 변경
  const couponInfos = coupon.couponInfos[0];

  const cafeName = coupon.cafeInfo.name;

  // FIXME: 이후 카페 관리 병합 후 parseUtil 사용
  return (
    <CouponDetailContainer $isDetail={isDetail}>
      <CafeImage src={cafe.cafeImageUrl} />
      <FlippedCoupon
        frontImageUrl={couponInfos.frontImageUrl}
        backImageUrl={couponInfos.backImageUrl}
        stampImageUrl={couponInfos.stampImageUrl}
        isShown={isShown}
        coordinates={couponInfos.coordinates}
      />
      <CloseButton onClick={closeDetail}>
        <BiArrowBack size={24} />
      </CloseButton>
      <OverviewContainer>
        <Text variant="subTitle">{cafeName}</Text>
        <Text>{cafe.introduction}</Text>
      </OverviewContainer>
      <ContentContainer>
        <Text>
          <FaRegBell size={24} />
          {`스탬프 ${couponInfos.maxStampCount}개를 채우면 ${couponInfos.rewardName} 무료!`}
        </Text>
        <Text>
          <FaRegClock size={24} />
          {`여는 시간 ${cafe.openTime}\n닫는 시간 ${cafe.closeTime}`}
        </Text>
        <Text>
          <FaPhoneAlt size={24} />
          {cafe.telephoneNumber}
        </Text>
        <Text>
          <FaLocationDot size={24} />
          {cafe.roadAddress + ' ' + cafe.detailAddress}
        </Text>
      </ContentContainer>
    </CouponDetailContainer>
  );
};

export default CouponDetail;
