import { useLocation, useNavigate } from 'react-router-dom';
import { Spacing } from '../../../style/layout/common';
import { useState } from 'react';
import { useMutation, useQuery } from '@tanstack/react-query';
import { CouponSelectorContainer, CouponSelectorWrapper } from './style';
import { getCoupon, getCurrentCouponDesign } from '../../../api/get';
import { postEarnStamp } from '../../../api/post';
import { INVALID_CAFE_ID, ROUTER_PATH } from '../../../constants';
import FlippedCoupon from '../../Customer/CouponList/components/FlippedCoupon';
import { useRedirectRegisterPage } from '../../../hooks/useRedirectRegisterPage';
import LoadingSpinner from '../../../components/LoadingSpinner';
import Button from '../../../components/Button';
import Stepper from '../../../components/Stepper';
import Text from '../../../components/Text';
import { IssuedCouponsRes } from '../../../types/api/response';

const EarnStamp = () => {
  const cafeId = useRedirectRegisterPage();
  const [stamp, setStamp] = useState(1);
  const { state } = useLocation();
  const navigate = useNavigate();

  const { mutate } = useMutation({
    mutationFn: postEarnStamp,
    onSuccess: () => {
      alert('스탬프 적립에 성공했습니다.');
      navigate(ROUTER_PATH.customerList);
    },
    onError: () => {
      throw new Error('스탬프 적립에 실패했습니다.');
    },
  });

  const { data: couponData, status: couponStatus } = useQuery<IssuedCouponsRes>(
    ['earn-stamp-coupons', state.customer],
    () => getCoupon({ params: { customerId: state.customer.id, cafeId } }),
    {
      enabled: cafeId !== INVALID_CAFE_ID,
    },
  );

  const { data: couponDesignData, status: couponDesignStatus } = useQuery({
    queryKey: ['couponDesignData', couponData],
    queryFn: () => {
      if (!couponData) throw new Error('쿠폰 정보를 불러오지 못했습니다.');

      return getCurrentCouponDesign({ params: { couponId: couponData.coupons[0].id, cafeId } });
    },
    enabled: !!couponData && couponData.coupons.length !== 0,
  });

  if (couponStatus === 'error' || couponDesignStatus === 'error') return <p>Error</p>;
  if (couponStatus === 'loading' || couponDesignStatus === 'loading') return <LoadingSpinner />;

  const earnStamp = () => {
    mutate({
      params: {
        customerId: Number(state.customer.id),
        couponId: couponData.coupons[0].id,
      },
      body: {
        earningStampCount: stamp,
      },
    });
  };

  return (
    <>
      <Spacing $size={40} />
      <Text variant="pageTitle">스탬프 적립</Text>
      <Spacing $size={40} />
      <Text variant="subTitle">
        step2. {state.customer.nickname} 고객에게 적립할 스탬프 갯수를 입력해주세요.
      </Text>
      <CouponSelectorContainer>
        <Stepper value={stamp} setValue={setStamp} />
        <CouponSelectorWrapper>
          <Text>
            현재 스탬프 개수: {couponData.coupons[0].stampCount}/
            {couponData.coupons[0].maxStampCount}
          </Text>
          <Spacing $size={8} />
          <FlippedCoupon
            frontImageUrl={couponDesignData.frontImageUrl}
            backImageUrl={couponDesignData.backImageUrl}
            stampImageUrl={couponDesignData.stampImageUrl}
            stampCount={couponData.coupons[0].stampCount}
            coordinates={couponDesignData.coordinates}
            isShown={true}
          />
          <Spacing $size={45} />
          <span>쿠폰 유효기간: {couponData.coupons[0].expireDate}까지</span>
        </CouponSelectorWrapper>
        <Button onClick={earnStamp}>적립</Button>
      </CouponSelectorContainer>
    </>
  );
};

export default EarnStamp;
