import { styled } from 'styled-components';

export const CouponContainer = styled.div`
  display: flex;
  position: relative;
  width: 270px;
  min-height: 150px;
  perspective: 1100px;
  transition: all 0.4s;
  box-shadow: 0px -10px 15px -2px rgba(0, 0, 0, 0.25);
`;

export const CouponWrapper = styled.div`
  position: absolute;
  width: 100%;
  height: 100%;
  transform-style: preserve-3d;
  backface-visibility: hidden;
  transition: all 0.4s;
  scroll-snap-align: end;

  &:active {
    transform: rotateY(180deg);
  }
`;

export const CouponImage = styled.img`
  position: absolute;
  width: 100%;
  height: 100%;
  transform-style: preserve-3d;
  backface-visibility: hidden;
  z-index: 2;
`;

export const FrontImage = styled(CouponImage)`
  z-index: 10;
`;

export const BackImage = styled(CouponImage)`
  transform: rotateY(180deg);
  z-index: 0;
`;

export const StampImage = styled.img`
  ${CouponWrapper}:active + & {
    visibility: visible;
  }

  visibility: hidden;
  width: 30px;
  height: 30px;
  position: absolute;
  top: 20px;
  left: 20px;
  background: yellow;
  z-index: 1;
`;
