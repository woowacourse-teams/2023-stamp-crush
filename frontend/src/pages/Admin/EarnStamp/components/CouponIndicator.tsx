import Text from '../../../../components/Text';
import { Spacing } from '../../../../style/layout/common';
import { IssuedCouponsRes } from '../../../../types/api/response';
import { CouponDesign } from '../../../../types/domain/coupon';
import { formatDate } from '../../../../utils';
import FlippedCoupon from '../../../Customer/CouponList/components/FlippedCoupon';
import { CouponIndicatorWrapper, TextWrapper } from '../style';

interface CouponIndicatorProps {
  couponDesignData: CouponDesign;
  coupon: IssuedCouponsRes;
  stamp: number;
}

const CouponIndicator = ({ couponDesignData, coupon, stamp }: CouponIndicatorProps) => {
  return (
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
  );
};

export default CouponIndicator;
