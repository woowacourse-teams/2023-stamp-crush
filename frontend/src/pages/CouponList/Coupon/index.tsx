import { CouponType } from '../../../types';
import { CouponWrapper } from './style';

interface CouponProps {
  coupon: CouponType;
  onClick: () => void;
}

const Coupon = ({ coupon, onClick }: CouponProps) => {
  return (
    <CouponWrapper
      src={coupon.couponInfos[0].frontImageUrl}
      onClick={onClick}
      aria-label={coupon.cafeInfo.name}
    />
  );
};

export default Coupon;
