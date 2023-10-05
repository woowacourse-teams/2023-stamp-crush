import { useLocation } from 'react-router-dom';
import { Spacing } from '../../../style/layout/common';
import { useEffect, useState } from 'react';
import {
  CouponSelectorContainer,
  CouponSelectorWrapper,
  StepperWrapper,
  TextWrapper,
} from './style';
import FlippedCoupon from '../../Customer/CouponList/components/FlippedCoupon';
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
import { formatDate, isNotEmptyArray } from '../../../utils';

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
        <CouponSelectorWrapper>
          <TextWrapper>
            <Text>현재 스탬프 개수:</Text>
            <Text>
              {coupon.coupons[0].stampCount} / {coupon.coupons[0].maxStampCount}
            </Text>
          </TextWrapper>
          <TextWrapper>
            <Text>스탬프 적립: </Text>
            <Text>{stamp}개</Text>
          </TextWrapper>
          <Spacing $size={8} />
          <FlippedCoupon
            frontImageUrl={couponDesignData.frontImageUrl}
            backImageUrl={couponDesignData.backImageUrl}
            stampImageUrl={couponDesignData.stampImageUrl}
            stampCount={coupon.coupons[0].stampCount + stamp}
            coordinates={couponDesignData.coordinates}
            isShown={true}
          />
          <span>쿠폰 유효기간: {formatDate(coupon.coupons[0].expireDate)}까지</span>
          {coupon.coupons[0].stampCount + stamp > coupon.coupons[0].maxStampCount && (
            <>
              <FlippedCoupon
                frontImageUrl={couponDesignData.frontImageUrl}
                backImageUrl={couponDesignData.backImageUrl}
                stampImageUrl={couponDesignData.stampImageUrl}
                stampCount={coupon.coupons[0].stampCount + stamp - coupon.coupons[0].maxStampCount}
                coordinates={couponDesignData.coordinates}
                isShown={true}
              />
              <p>새로 발급되는 쿠폰입니다.</p>
            </>
          )}
          <Spacing $size={5} />
        </CouponSelectorWrapper>
        <StepperWrapper>
          <Stepper value={stamp} setValue={setStamp} />
          {coupon.coupons[0].stampCount + stamp > coupon.coupons[0].maxStampCount && (
            <p>[알림] 지금부터 적립하는 스탬프는 새 쿠폰에 적립됩니다.</p>
          )}
          <Button onClick={earnStamp}>적립</Button>
        </StepperWrapper>
        {/**TODO: 보유 쿠폰의 발급 시기와 새 쿠폰의 발급 시기에 차이가 있는 케이스에 대한 대처가 필요 */}
      </CouponSelectorContainer>
    </>
  );
};

export default EarnStamp;
