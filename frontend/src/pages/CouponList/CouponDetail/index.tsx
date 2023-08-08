import { MouseEvent, useEffect } from 'react';
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
import { Coupon } from '../../../types';
import { parsePhoneNumber } from '../../../utils';
import { useQuery } from '@tanstack/react-query';
import { CafeRes } from '../../../types/api';
import { getCafeInfo } from '../../../api/get';

interface CouponDetailProps {
  isDetail: boolean;
  isShown: boolean;
  coupon: Coupon;
  closeDetail: (e: MouseEvent<HTMLButtonElement>) => void;
}

const CouponDetail = ({ isDetail, isShown, coupon, closeDetail }: CouponDetailProps) => {
  const [couponInfos] = coupon.couponInfos;
  const cafeId = coupon.cafeInfo.id;

  const { data: cafeData, status: cafeStatus } = useQuery<CafeRes>(['cafeInfos', cafeId], {
    queryFn: () => getCafeInfo(cafeId),
    enabled: cafeId !== 0,
  });

  // TODO: 로딩, 에러 컴포넌트 만들기
  if (cafeStatus === 'loading') return null;
  if (cafeStatus === 'error') return null;

  return (
    <CouponDetailContainer $isDetail={isDetail}>
      <CafeImage src={cafeData.cafe.cafeImageUrl} />
      <FlippedCoupon
        frontImageUrl={couponInfos.frontImageUrl}
        backImageUrl={couponInfos.backImageUrl}
        stampImageUrl={couponInfos.stampImageUrl}
        isShown={isShown}
        coordinates={couponInfos.coordinates}
        stampCount={couponInfos.stampCount}
      />
      <CloseButton onClick={closeDetail} aria-label="홈으로 돌아가기" role="button">
        <BiArrowBack size={24} />
      </CloseButton>
      <OverviewContainer>
        <Text variant="subTitle">{coupon.cafeInfo.name}</Text>
        <Text>{cafeData.cafe.introduction}</Text>
      </OverviewContainer>
      <ContentContainer>
        <Text ariaLabel="쿠폰 정책">
          <FaRegBell size={22} />
          {`스탬프 ${couponInfos.maxStampCount}개를 채우면 ${couponInfos.rewardName} 무료!`}
        </Text>
        <Text ariaLabel="영업 시간">
          <FaRegClock size={22} />
          {`${cafeData.cafe.openTime} - ${cafeData.cafe.closeTime}`}
        </Text>
        <Text ariaLabel="전화번호">
          <FaPhoneAlt size={22} />
          {parsePhoneNumber(cafeData.cafe.telephoneNumber)}
        </Text>
        <Text ariaLabel="주소">
          <FaLocationDot size={22} />
          {cafeData.cafe.roadAddress + ' ' + cafeData.cafe.detailAddress}
        </Text>
      </ContentContainer>
    </CouponDetailContainer>
  );
};

export default CouponDetail;
