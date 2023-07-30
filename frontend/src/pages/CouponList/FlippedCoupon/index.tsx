import { useEffect, useState } from 'react';
import { BackImage, CouponContainer, CouponWrapper, FrontImage, StampImage } from './style';

interface StampCoordinate {
  order: number;
  xCoordinate: number;
  yCoordinate: number;
}

interface FlippedCouponProps {
  frontImageUrl: string;
  backImageUrl: string;
  stampImageUrl: string;
  isShown: boolean;
  coordinates: StampCoordinate[];
}

const FlippedCoupon = ({
  frontImageUrl,
  backImageUrl,
  stampImageUrl,
  isShown,
  coordinates,
}: FlippedCouponProps) => {
  const [isFlipped, setIsFlipped] = useState(false);

  useEffect(() => {
    if (!isShown) setIsFlipped(false);
  }, [isShown]);

  const flipCoupon = () => {
    setIsFlipped(!isFlipped);
  };

  return (
    <CouponContainer $isShown={isShown} onClick={flipCoupon}>
      <CouponWrapper $isFlipped={isFlipped}>
        <FrontImage src={frontImageUrl} />
        <BackImage src={backImageUrl} />
        {coordinates &&
          coordinates.map(({ order, xCoordinate, yCoordinate }, idx) => (
            <StampImage
              key={order + idx}
              src={stampImageUrl}
              $xCoordinate={xCoordinate}
              $yCoordinate={yCoordinate}
            />
          ))}
      </CouponWrapper>
    </CouponContainer>
  );
};

export default FlippedCoupon;
