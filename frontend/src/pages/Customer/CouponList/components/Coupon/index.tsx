import { useState } from 'react';
import { Coupon as CouponType } from '../../../../../types/domain/coupon';
import { CouponWrapper, ImageForLoading } from './style';
import CouponLoading from '../../../../../assets/coupon_load_img_for_customer.png';

interface CouponProps {
  coupon: CouponType;
  dataIndex: number;
  isOn: boolean;
  index: number;
  onClick: () => void;
}

const Coupon = ({ coupon, dataIndex, onClick, isOn, index }: CouponProps) => {
  const [isImageLoaded, setIsImageLoaded] = useState(false);

  const checkLoadImage = () => {
    setIsImageLoaded(true);
  };

  return (
    <CouponWrapper
      $src={isImageLoaded ? coupon.couponInfos[0].frontImageUrl : CouponLoading}
      onClick={onClick}
      aria-label={coupon.cafeInfo.name}
      data-index={dataIndex}
      $isOn={isOn}
      $index={index}
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
