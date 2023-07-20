import { useLocation, useNavigate } from 'react-router-dom';
import Button from '../../../components/Button';
import Header from '../../../components/Header';
import Template from '../../../components/Template';
import { RowSpacing, Spacing, SubTitle, Text, Title } from '../../../style/layout/common';
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
import { getCoupon } from '../SelectCoupon';
import { BASE_URL } from '../../..';

interface StampFormData {
  earningStampCount: number;
  customerId: string;
  couponId: string;
  ownerId: string;
}

const postEarnStamp = async ({
  earningStampCount,
  customerId,
  couponId,
  ownerId,
}: StampFormData) => {
  const response = await fetch(`${BASE_URL}/customers/${customerId}/coupons/${couponId}/stamps/${ownerId}`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify({ earningStampCount }),
  });

  if (!response.ok) {
    throw new Error('스탬프 적립에 실패했습니다.');
  }
};

const EarnStamp = () => {
  const [stamp, setStamp] = useState(1);

  const { state } = useLocation();
  const navigate = useNavigate();

  const { mutate, isLoading, isError } = useMutation(
    (formData: StampFormData) => postEarnStamp(formData),
    {
      onSuccess: () => {
        navigate('/admin');
      },
      onError: () => {
        throw new Error('스탬프 적립에 실패했습니다.');
      },
    },
  );

  const {
    data: couponResponse,
    isError: isCouponError,
    isLoading: isCouponLoading,
  } = useQuery(['earn-stamp-coupons', state.customer], () => getCoupon(state.customer.id, '1'));

  const earnStamp = () => {
    const stampData = {
      earningStampCount: stamp,
      customerId: state.customer.id,
      couponId: couponResponse.coupons[0].id,
      ownerId: '1',
    };
    mutate(stampData);
  };

  if (isLoading || isCouponLoading) return <p>Loading</p>;

  if (isError || isCouponError) return <p>Error</p>;

  return (
    <>
      <Header />
      <Template>
        <EarnStampContainer>
          <TitleWrapper>
            <Title>스탬프 적립</Title>
            <SubTitle>2/2</SubTitle>
          </TitleWrapper>
          <Spacing $size={90} />
          <SubTitle>{state.customer.nickname} 고객님의 현재 쿠폰</SubTitle>
          <Spacing $size={80} />
          <CouponSelectorContainer>
            <CouponSelectorWrapper>
              <Text>
                현재 스탬프 개수: {couponResponse.coupons[0].stampCount}/{8}
              </Text>
              <Spacing $size={8} />
              <img src="https://picsum.photos/seed/picsum/270/150" width={270} height={150} />
              <Spacing $size={45} />
              <ExpirationDate>
                쿠폰 유효기간: {couponResponse.coupons[0].expireDate}까지
              </ExpirationDate>
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
      </Template>
    </>
  );
};

export default EarnStamp;
