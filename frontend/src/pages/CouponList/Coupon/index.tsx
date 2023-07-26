import { CouponWrapper } from './style';

interface CouponProps {
  frontImageUrl: string;
  onClick: () => void;
}

const Coupon = ({ frontImageUrl, onClick }: CouponProps) => {
  return <CouponWrapper src={frontImageUrl} onClick={onClick}></CouponWrapper>;
};

export default Coupon;
