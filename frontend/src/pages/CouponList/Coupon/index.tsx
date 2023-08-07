import { Coupon as CouponType } from '../../../types';
import { CouponWrapper } from './style';

interface CouponProps {
  coupon: CouponType;
  isFocused: boolean;
  onClick: () => void;
}

const Coupon = ({ coupon, isFocused, onClick }: CouponProps) => {
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
