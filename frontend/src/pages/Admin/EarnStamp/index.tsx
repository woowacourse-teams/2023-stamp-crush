import { useLocation } from 'react-router-dom';
import { Spacing } from '../../../style/layout/common';
import { useEffect, useState } from 'react';
import { CouponSelectorContainer, CouponSelectorWrapper } from './style';
import FlippedCoupon from '../../Customer/CouponList/FlippedCoupon';
import { useRedirectRegisterPage } from '../../../hooks/useRedirectRegisterPage';
import LoadingSpinner from '../../../components/LoadingSpinner';
import Button from '../../../components/Button';
import Stepper from '../../../components/Stepper';
import Text from '../../../components/Text';
import usePostEarnStamp from './hooks/usePostEarnStamp';
import { CustomerPhoneNumber } from '../../../types/domain/customer';
import usePostIssueCoupon from './hooks/usePostIssueCoupon';
import useGetCoupon from './hooks/useGetCoupon';
import useGetCurrentCouponDesign from './hooks/useGetCurrentCouponDesign';
import { isNotEmptyArray } from '../../../utils';

const EarnStamp = () => {
  const cafeId = useRedirectRegisterPage();
  const [stamp, setStamp] = useState(1);
  const { state } = useLocation();
  const location = useLocation();
  const customer: CustomerPhoneNumber = location.state;

  const { mutate: mutatePostEarnStamp } = usePostEarnStamp();
  const { mutate: mutateIssueCoupon } = usePostIssueCoupon(cafeId, customer);

  const { data: coupon, status: couponStatus } = useGetCoupon(cafeId, customer);
  const { data: couponDesignData, status: couponDesignStatus } = useGetCurrentCouponDesign(
    cafeId,
    coupon,
  );

  useEffect(() => {
    if (coupon && !isNotEmptyArray(coupon.coupons)) {
      mutateIssueCoupon();
    }
  }, [coupon]);

  if (couponStatus === 'error' || couponDesignStatus === 'error') return <p>Error</p>;
  if (couponStatus === 'loading' || couponDesignStatus === 'loading') return <LoadingSpinner />;

  const earnStamp = () => {
    mutatePostEarnStamp({
      params: {
        customerId: Number(state.id),
        couponId: coupon.coupons[0].id,
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
      <Text variant="subTitle">{state.nickname} 고객에게 적립할 스탬프 갯수를 입력해주세요.</Text>
      <CouponSelectorContainer>
        <Stepper value={stamp} setValue={setStamp} />
        <CouponSelectorWrapper>
          <Text>
            현재 스탬프 개수: {coupon.coupons[0].stampCount}/{coupon.coupons[0].maxStampCount}
          </Text>
          <Text>스탬프 적립: {stamp}개</Text>
          <Spacing $size={8} />
          <FlippedCoupon
            frontImageUrl={couponDesignData.frontImageUrl}
            backImageUrl={couponDesignData.backImageUrl}
            stampImageUrl={couponDesignData.stampImageUrl}
            stampCount={coupon.coupons[0].stampCount + stamp}
            coordinates={couponDesignData.coordinates}
            isShown={true}
          />
          <Spacing $size={5} />
          <span>쿠폰 유효기간: {coupon.coupons[0].expireDate}까지</span>
        </CouponSelectorWrapper>
        <Button onClick={earnStamp}>적립</Button>
      </CouponSelectorContainer>
    </>
  );
};

export default EarnStamp;
