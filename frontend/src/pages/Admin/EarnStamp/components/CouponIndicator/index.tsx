import { useEffect } from 'react';
import Button from '../../../../../components/Button';
import Text from '../../../../../components/Text';
import { useRedirectRegisterPage } from '../../../../../hooks/useRedirectRegisterPage';
import { Spacing } from '../../../../../style/layout/common';
import { formatDate, isNotEmptyArray } from '../../../../../utils';
import FlippedCoupon from '../../../../Customer/CouponList/components/FlippedCoupon';
import useGetCoupon from '../../hooks/useGetCoupon';
import useGetCurrentCouponDesign from '../../hooks/useGetCurrentCouponDesign';
import { CouponIndicatorWrapper, StepperWrapper, TextWrapper } from '../../style';
import { CustomerPhoneNumber } from '../../../../../types/domain/customer';
import usePostEarnStamp from '../../hooks/usePostEarnStamp';
import usePostIssueCoupon from '../../hooks/usePostIssueCoupon';
import EarnStampSkeleton from '../EarnStampSkeleton';
import LoadingSpinner from '../LoadingSpinner';
import Alert from '../../../../../components/Alert';
import { useNavigate } from 'react-router-dom';
import ROUTER_PATH from '../../../../../constants/routerPath';
import { EmptyCouponIndicator } from './style';

interface CouponIndicatorProps {
  stamp: number;
  customer: CustomerPhoneNumber;
  customerId: number;
}

const CouponIndicator = ({ stamp, customer, customerId }: CouponIndicatorProps) => {
  const cafeId = useRedirectRegisterPage();
  const navigate = useNavigate();

  const { mutate: mutatePostEarnStamp, status: earnStampStatus } = usePostEarnStamp();
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

  if (couponStatus === 'error' || couponDesignStatus === 'error')
    return (
      <EmptyCouponIndicator>
        예기치 못한 오류가 발생하여 쿠폰을 불러오는 데 실패했습니다.
      </EmptyCouponIndicator>
    );
  if (couponStatus === 'loading' || couponDesignStatus === 'loading') return <EarnStampSkeleton />;

  const earnStamp = () => {
    mutatePostEarnStamp({
      params: {
        customerId,
        couponId: coupon.coupons[0].id,
      },
      body: {
        earningStampCount: stamp,
      },
    });
  };

  const goHome = () => {
    navigate(ROUTER_PATH.customerList);
  };

  return (
    <>
      <CouponIndicatorWrapper>
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
      </CouponIndicatorWrapper>
      <StepperWrapper>
        <Button onClick={earnStamp}>
          {earnStampStatus === 'loading' ? <LoadingSpinner /> : '적립'}
        </Button>
      </StepperWrapper>
      {earnStampStatus === 'error' && (
        <Alert
          text={'예기치 못한 오류가 발생하여\n쿠폰 적립에 실패했습니다.'}
          rightOption={'홈으로 돌아가기'}
          leftOption={'재시도'}
          onClickRight={goHome}
          onClickLeft={earnStamp}
        />
      )}
    </>
  );
};

export default CouponIndicator;
