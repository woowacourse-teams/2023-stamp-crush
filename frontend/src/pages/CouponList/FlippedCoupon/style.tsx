import { css, styled } from 'styled-components';

export const CouponContainer = styled.div<{ $isShown: boolean }>`
  display: ${({ $isShown }) => ($isShown ? 'flex' : 'none')};
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

  z-index: 2;
`;

export const FrontImage = styled(CouponImage)`
  z-index: 10;
`;

export const BackImage = styled(CouponImage)`
  transform: rotateY(180deg);
  z-index: 0;
`;

export const StampImage = styled.img<{ $x: number; $y: number }>`
  width: 30px;
  height: 30px;
  position: absolute;
  top: ${({ $y }) => `${$y - 15}px`};
  right: ${({ $x }) => `${$x - 15}px`};
  object-fit: cover;
  backface-visibility: hidden;
  transform-style: preserve-3d;
  transform: rotateY(180deg);
  z-index: 1;
`;
