import { useLocation, useNavigate } from 'react-router-dom';
import Button from '../../../components/Button';
import Header from '../../../components/Header';
import Template from '../../../components/Template';
import { SubTitle, Text, Title } from '../../../style/layout/common';
import { ExpirationDate, TitleWrapper } from '../SelectCoupon/style';
import { useState } from 'react';
import Stepper from '../../../components/Stepper';
import { useMutation, useQuery } from '@tanstack/react-query';
import { EarnStampContainer } from './style';
import { getCoupon } from '../SelectCoupon';

const postEarnStamp = async (earningStampCount: number, customerId: string, couponId: string) => {
  const response = await fetch(`/customers/${customerId}/coupons/${couponId}/stamps`, {
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

  console.log('state', state);
  const { mutate, isLoading, isError } = useMutation(
    (earningStampCount: number) => postEarnStamp(earningStampCount, '1', '1'),
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
    mutate(stamp);
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
          <SubTitle>{state.customer.nickname} 고객님의 현재 쿠폰</SubTitle>

          <>
            <Text>
              현재 스탬프 개수: {couponResponse.coupons[0].stampCount}/{8}
            </Text>
            <ExpirationDate>
              쿠폰 유효기간: {couponResponse.coupons[0].expireDate}까지
            </ExpirationDate>
          </>
          <SubTitle>현재 쿠폰에 적립할 스탬프 개수를 설정해주세요.</SubTitle>
          <Stepper value={stamp} setValue={setStamp} />
          <Button onClick={earnStamp}>적립하기</Button>
        </EarnStampContainer>
      </Template>
    </>
  );
};

export default EarnStamp;
