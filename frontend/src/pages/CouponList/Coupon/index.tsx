import { Coupon as CouponType } from '../../../types';
import { CouponWrapper } from './style';

interface CouponProps {
  coupon: CouponType;
  onClick: () => void;
  isFocused: boolean;
}

const Coupon = ({ coupon, onClick, isFocused }: CouponProps) => {
  return (
    <CouponWrapper
      $src={coupon.couponInfos[0].frontImageUrl}
      onClick={onClick}
      aria-label={coupon.cafeInfo.name}
      aria-hidden={isFocused ? 'false' : 'true'}
      disabled={!isFocused}
    />
  );
};

export default Coupon;
