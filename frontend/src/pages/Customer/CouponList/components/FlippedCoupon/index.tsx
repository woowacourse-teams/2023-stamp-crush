import { StampCoordinate } from '../../../../../types/domain/coupon';
import { StampImage } from './style';
import { TurnableComponent } from 'react-turnable-component';

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
  return (
    <TurnableComponent
      frontImageUrl={frontImageUrl}
      backImageUrl={backImageUrl}
      isFirstTurned={isShown}
    >
      {coordinates &&
        coordinates
          .filter((_, idx) => idx + 1 <= stampCount)
          .map(({ order, xCoordinate, yCoordinate }, idx) => (
            <StampImage key={order + idx} src={stampImageUrl} $x={xCoordinate} $y={yCoordinate} />
          ))}
    </TurnableComponent>
  );
};

export default FlippedCoupon;
