import { useLocation, useNavigate } from 'react-router-dom';
import Button from '../../../components/Button';
import { Spacing } from '../../../style/layout/common';
import { useState } from 'react';
import Stepper from '../../../components/Stepper';
import { useMutation, useQuery } from '@tanstack/react-query';
import { CouponSelectorContainer, CouponSelectorWrapper } from './style';
import { getCoupon } from '../../../api/get';
import { postEarnStamp } from '../../../api/post';
import Text from '../../../components/Text';
import { ROUTER_PATH } from '../../../constants';
import { IssuedCouponsRes } from '../../../types/api';

const EarnStamp = () => {
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
    () => getCoupon({ params: { customerId: state.customer.id, cafeId: 1 } }),
  );

  if (couponStatus === 'error') return <p>Error</p>;
  if (couponStatus === 'loading') return <p>Loading</p>;

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
            현재 스탬프 개수: {couponData.coupons[0].stampCount}/{10}
          </Text>
          <Spacing $size={8} />
          <img src="https://picsum.photos/seed/picsum/270/150" width={270} height={150} />
          <Spacing $size={8} />
          <span>쿠폰 유효기간: {couponData.coupons[0].expireDate}까지</span>
        </CouponSelectorWrapper>
        <Button onClick={earnStamp}>적립</Button>
      </CouponSelectorContainer>
    </>
  );
};

export default EarnStamp;
