import { useLocation, useNavigate } from 'react-router-dom';
import Button from '../../../components/Button';
import { RowSpacing, Spacing } from '../../../style/layout/common';
import {
  CouponSelectorContainer,
  CouponSelectorWrapper,
  ExpirationDate,
  TitleWrapper,
} from '../SelectCoupon/style';
import { useState } from 'react';
import Stepper from '../../../components/Stepper';
import { useMutation, useQuery } from '@tanstack/react-query';
import { CouponStepperWrapper, EarnStampContainer, StepperGuide } from './style';
import { getCoupon } from '../../../api/get';
import { postEarnStamp } from '../../../api/post';
import Text from '../../../components/Text';
import { ROUTER_PATH } from '../../../constants';
import { IssuedCouponsRes, StampEarningReq } from '../../../types/api';

const EarnStamp = () => {
  const [stamp, setStamp] = useState(1);
  const { state } = useLocation();
  const navigate = useNavigate();

  const { mutate } = useMutation<void, Error, StampEarningReq>(
    (formData: StampEarningReq) => postEarnStamp(formData),
    {
      onSuccess: () => {
        navigate(ROUTER_PATH.customerList);
      },
      onError: () => {
        throw new Error('스탬프 적립에 실패했습니다.');
      },
    },
  );

  const { data: couponData, status: couponStatus } = useQuery<IssuedCouponsRes, Error>(
    ['earn-stamp-coupons', state.customer],
    () => getCoupon(state.customer.id, '1'),
  );

  if (couponStatus === 'error') return <p>Error</p>;
  if (couponStatus === 'loading') return <p>Loading</p>;

  const earnStamp = () => {
    const stampData = {
      earningStampCount: stamp,
      customerId: Number(state.customer.id),
      couponId: couponData.coupons[0].id,
    };
    mutate(stampData);
  };

  return (
    <EarnStampContainer>
      <TitleWrapper>
        <Text variant="pageTitle">스탬프 적립</Text>
        <Text variant="subTitle">2/2</Text>
      </TitleWrapper>
      <Spacing $size={90} />
      <Text variant="subTitle">{state.customer.nickname} 고객님의 현재 쿠폰</Text>
      <Spacing $size={80} />
      <CouponSelectorContainer>
        <CouponSelectorWrapper>
          <Text>
            현재 스탬프 개수: {couponData.coupons[0].stampCount}/{10}
          </Text>
          <Spacing $size={8} />
          <img src="https://picsum.photos/seed/picsum/270/150" width={270} height={150} />
          <Spacing $size={45} />
          <ExpirationDate>쿠폰 유효기간: {couponData.coupons[0].expireDate}까지</ExpirationDate>
        </CouponSelectorWrapper>
        <RowSpacing $size={110} />
        <CouponStepperWrapper>
          <StepperGuide>
            현재 쿠폰에 적립할
            <br /> 스탬프 개수를 설정해주세요.
          </StepperGuide>
          <Spacing $size={30} />
          <Stepper value={stamp} setValue={setStamp} />
        </CouponStepperWrapper>
      </CouponSelectorContainer>
      <Spacing $size={70} />
      <Button onClick={earnStamp}>적립하기</Button>
    </EarnStampContainer>
  );
};

export default EarnStamp;
