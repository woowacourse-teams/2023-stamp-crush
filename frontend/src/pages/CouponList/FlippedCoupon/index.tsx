import { useState } from 'react';
import { BackImage, CouponContainer, CouponWrapper, FrontImage, StampImage } from './style';

interface StampCoordinate {
  order: number;
  xCoordinate: number;
  yCoordinate: number;
}

interface FlippedCouponProps {
  frontImageUrl: string;
  backImageUrl: string;
  isShown: boolean;
  coordinates: StampCoordinate[];
}

const FlippedCoupon = ({
  frontImageUrl,
  backImageUrl,
  isShown,
  coordinates,
}: FlippedCouponProps) => {
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
      {coordinates &&
        coordinates.map(({ order, xCoordinate, yCoordinate }, idx) => (
          <StampImage
            key={order + idx}
            $isFlipped={isFlipped}
            $xCoordinate={xCoordinate}
            $yCoordinate={yCoordinate}
          />
        ))}
    </CouponContainer>
  );
};

export default FlippedCoupon;
