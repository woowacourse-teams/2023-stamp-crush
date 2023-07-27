import { css, styled } from 'styled-components';
import { swap } from '../../style/keyframes';

interface StyledListProps {
  $isLast: boolean;
}

export const CafeName = styled.span`
  font-size: 36px;
  font-weight: 700;
`;

export const CouponListContainer = styled.div<StyledListProps>`
  display: flex;
  justify-content: center;
  position: relative;
  height: 100vh;

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
