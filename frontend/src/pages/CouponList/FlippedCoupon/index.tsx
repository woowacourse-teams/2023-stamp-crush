import { useState } from 'react';
import { BackImage, CouponContainer, CouponWrapper, FrontImage, StampImage } from './style';

interface FlippedCouponProps {
  frontImageUrl: string;
  backImageUrl: string;
  isShown: boolean;
}

const FlippedCoupon = ({ frontImageUrl, backImageUrl, isShown }: FlippedCouponProps) => {
  const [isFlipped, setIsFlipped] = useState(false);

  const flipCoupon = () => {
    setIsFlipped(!isFlipped);
  };

  return (
    <CouponContainer $isShown={isShown} onClick={flipCoupon}>
      <CouponWrapper $isFlipped={isFlipped}>
        <FrontImage src={frontImageUrl} />
        <BackImage src={backImageUrl} />
      </CouponWrapper>
      <StampImage $isFlipped={isFlipped} />
    </CouponContainer>
  );
};

export default FlippedCoupon;
