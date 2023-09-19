import { useEffect, useState } from 'react';
import { StampCoordinate } from '../../../../types/domain/coupon';
import { BackImage, CouponContainer, CouponWrapper, FrontImage, StampImage } from './style';

export interface FlippedCouponProps {
  frontImageUrl: string;
  backImageUrl: string;
  stampImageUrl: string;
  isShown: boolean;
  stampCount: number;
  coordinates: StampCoordinate[];
}

const FlippedCoupon = ({
  frontImageUrl,
  backImageUrl,
  stampImageUrl,
  isShown,
  stampCount,
  coordinates,
}: FlippedCouponProps) => {
  const [isFlipped, setIsFlipped] = useState(false);

  useEffect(() => {
    if (isShown) {
      setTimeout(() => {
        setIsFlipped(true);
      }, 100);
    }

    if (!isShown) {
      setIsFlipped(false);
    }
  }, [isShown]);

  return (
    <CouponContainer>
      <CouponWrapper $isFlipped={isFlipped}>
        <FrontImage src={frontImageUrl} />
        <BackImage src={backImageUrl} />
        {coordinates &&
          coordinates
            .filter((_, idx) => idx + 1 <= stampCount)
            .map(({ order, xCoordinate, yCoordinate }, idx) => (
              <StampImage key={order + idx} src={stampImageUrl} $x={xCoordinate} $y={yCoordinate} />
            ))}
      </CouponWrapper>
    </CouponContainer>
  );
};

export default FlippedCoupon;
