import { css, styled } from 'styled-components';
import { swap } from '../../style/keyframes';

interface StyledListProps {
  $isLast: boolean;
  $couponMainColor: string;
}

export const HeaderContainer = styled.header`
  display: flex;
  height: 65px;
  justify-content: space-between;
  align-items: center;
  padding: 0 20px;
  border-bottom: 2px solid #eeeeee;

  & > div {
    display: flex;
    align-items: center;
    gap: 10px;
  }
`;

export const LogoImg = styled.img`
  height: 24px;
`;

export const InfoContainer = styled.div`
  display: flex;
  flex-direction: column;
  padding: 50px 30px;
  gap: 20px;
`;

export const NameContainer = styled.div`
  display: flex;
  align-items: center;
  gap: 15px;
`;

export const CafeName = styled.span`
  font-size: 36px;
  font-weight: 700;
`;

export const ProgressBarContainer = styled.div`
  display: flex;
  align-items: center;
  justify-content: space-between;
  font-size: 24px;
  color: #888;
`;

export const StampCount = styled.span`
  margin-left: 10px;
  font-size: 36px;
  font-weight: 600;
  color: black;
`;

export const MaxStampCount = styled.span`
  font-size: 24px;
  color: #f3b209;
`;

export const BackDrop = styled.div<{ $couponMainColor: string }>`
  z-index: -10;
  width: 100vw;
  height: 100vh;
  position: fixed;
  top: 0;
  right: 0;
  background: linear-gradient(
    white,
    rgba(255, 255, 255, 0.5) 32%,
    ${(props) => props.$couponMainColor} 80%,
    white
  );
  opacity: 0.7;
`;

export const CouponListContainer = styled.div<{ $isLast: boolean }>`
  display: flex;
  justify-content: center;
  position: relative;
  min-height: 700px;

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
