import { BackImage, CouponContainer, CouponWrapper, FrontImage, StampImage } from './style';
import { ImgHTMLAttributes } from 'react';

interface CouponProps extends ImgHTMLAttributes<HTMLImageElement> {
  frontImageUrl?: string;
  backImageUrl?: string;
}

const Coupon = ({ frontImageUrl, backImageUrl, ...props }: CouponProps) => {
  return (
    <CouponContainer>
      <CouponWrapper>
        <FrontImage src={frontImageUrl} {...props} />
        <BackImage src={backImageUrl} {...props} />
      </CouponWrapper>
      <StampImage />
    </CouponContainer>
  );
};

export default Coupon;
