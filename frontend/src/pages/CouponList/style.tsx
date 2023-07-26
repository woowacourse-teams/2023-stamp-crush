import { css, styled } from 'styled-components';
import { swap } from '../../style/keyframes';

interface StyledListProps {
  $isLast: boolean;
}

export const CouponListContainer = styled.div<StyledListProps>`
  width: 500px;
  height: 500px;
  position: relative;

  :nth-last-child(1) {
    transform: translateY(15px) scale(1.05);
    animation: ${({ $isLast }) =>
      $isLast
        ? css`
            ${swap} 0.7s forwards
          `
        : 'none'};
    cursor: pointer;
    &:hover {
      transform: scale(1.1);
    }
  }
  :nth-last-child(2) {
    transform: translateY(0px) scale(1);
  }
  :nth-last-child(3) {
    transform: translateY(-15px) scale(0.95);
  }
  :nth-last-child(n + 4) {
    transform: translateY(-30px) scale(0.9);
  }
`;

export const Test = styled.div`
  width: 270px;
  height: 150px;
  position: absolute;
  top: 50%;
  bottom: 50%;
  transform: translate(50%, -50%);
  transition: transform 0.1s;
`;
