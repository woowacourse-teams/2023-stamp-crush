import { useEffect, useState } from 'react';
import {
  BackImage,
  CouponContainer,
  CouponNotification,
  CouponWrapper,
  FrontImage,
  StampImage,
} from './style';
import { StampCoordinate } from '../../../types';

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
    <>
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
      <CouponNotification>
        {isFlipped
          ? '쿠폰을 터치하여 쿠폰 앞면으로 돌아갑니다.'
          : '쿠폰을 터치하여 스탬프를 확인할 수 있습니다.'}
      </CouponNotification>
    </>
  );
};

export default FlippedCoupon;
