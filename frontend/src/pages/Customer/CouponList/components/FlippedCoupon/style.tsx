import { css, styled } from 'styled-components';
import { Z_INDEX } from '../../../../../constants/magicNumber';

export const CouponContainer = styled.div`
  display: flex;
  position: relative;
  width: 270px;
  height: 150px;
  perspective: 1100px;
  transition: all 0.4s;
`;

export const CouponWrapper = styled.div<{ $isFlipped: boolean }>`
  position: absolute;
  width: 100%;
  height: 100%;
  transform-style: preserve-3d;
  backface-visibility: hidden;
  transition: all 0.4s;

  ${({ $isFlipped }) =>
    $isFlipped &&
    css`
      transform: rotateY(180deg);
    `}
`;

export const CouponImage = styled.img`
  position: absolute;
  width: 100%;
  height: 100%;
  transform-style: preserve-3d;
  object-fit: cover;
  backface-visibility: hidden;
  box-shadow: 0px -2px 15px -2px #888;
`;

export const FrontImage = styled(CouponImage)`
  z-index: ${Z_INDEX.above};
`;

export const BackImage = styled(CouponImage)`
  transform: rotateY(180deg);
  z-index: ${Z_INDEX.base};
`;

export const StampImage = styled.img<{ $x: number; $y: number }>`
  width: 35px;
  height: 35px;
  position: absolute;
  top: ${({ $y }) => `${$y - 17.5}px`};
  right: ${({ $x }) => `${$x - 17.5}px`};
  object-fit: contain;
  backface-visibility: hidden;
  transform-style: preserve-3d;
  transform: rotateY(180deg);
  z-index: ${Z_INDEX.above};
`;
