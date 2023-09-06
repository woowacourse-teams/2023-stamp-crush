import { useState } from 'react';
import { Coupon as CouponType } from '../../../../types';
import { CouponWrapper, ImageForLoading } from './style';
import CouponLoading from '../../../../assets/coupon_load_img_for_customer.png';

interface CouponProps {
  coupon: CouponType;
  isFocused: boolean;
  onClick: () => void;
}

const Coupon = ({ coupon, isFocused, onClick }: CouponProps) => {
  const [isImageLoaded, setIsImageLoaded] = useState(false);
  const checkLoadImage = () => {
    setIsImageLoaded(true);
  };

  return (
    <CouponWrapper
      $src={isImageLoaded ? coupon.couponInfos[0].frontImageUrl : CouponLoading}
      onClick={onClick}
      aria-label={coupon.cafeInfo.name}
      aria-hidden={isFocused ? 'false' : 'true'}
      disabled={!isFocused}
    >
      <ImageForLoading
        src={coupon.couponInfos[0].frontImageUrl}
        onLoad={checkLoadImage}
        alt={coupon.cafeInfo.name}
      />
    </CouponWrapper>
  );
};

export default Coupon;
