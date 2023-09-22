import { css, styled } from 'styled-components';
import { swap } from '../../../style/keyframes';

export const HeaderContainer = styled.header`
  display: flex;
  width: 100%;
  height: 65px;
  justify-content: space-between;
  align-items: center;
  padding: 0 25px;
  border-bottom: 2px solid #eeeeee;
`;

export const LogoImg = styled.img`
  height: 25px;
`;

export const InfoContainer = styled.div`
  display: flex;
  flex-direction: column;
  padding: 60px 30px;
  gap: 20px;
`;

export const NameContainer = styled.div`
  display: flex;
  align-items: center;
  justify-content: space-between;
  height: 36px;
  gap: 10px;
`;

export const CafeName = styled.span`
  font-size: 36px;
  font-weight: 700;
  margin-top: 5px;
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
  width: 100%;
  max-width: 450px;
  overflow: hidden;
  height: 100%;
  position: absolute;
  top: 0;
  right: 0;
  background: linear-gradient(
    white,
    rgba(255, 255, 255, 0.5) 32%,
    ${(props) => props.$couponMainColor} 80%,
    white
  );
  opacity: 0.7;
  left: 50%;
  transform: translateX(-50%);
`;

export const CouponListContainer = styled.div<{
  $isLast: boolean;
  $isDetail: boolean;
  $isShown: boolean;
}>`
  display: flex;
  justify-content: center;
  position: relative;
  height: 150px;
  transition: all 0.1s;

  /** iPhone SE */
  @media only screen and (min-device-width: 320px) and (max-device-width: 375px) {
    height: 10px;
  }

  :nth-last-child(1) {
    z-index: ${({ $isShown }) => ($isShown ? '-1' : '3')};
    transform: translateY(15px) scale(1.05);
    animation: ${({ $isLast }) =>
      $isLast
        ? css`
            ${swap} 0.5s forwards
          `
        : 'none'};
    transition: transform 0.4s;
    cursor: pointer;
  }

  ${({ $isDetail }) =>
    $isDetail &&
    css`
      :nth-last-child(n) {
        display: none;
      }
    `}

  :nth-last-child(2) {
    transform: translateY(0px) scale(1);
    pointer-events: none;
  }
  :nth-last-child(3) {
    transform: translateY(-15px) scale(0.95);
    pointer-events: none;
  }
  :nth-last-child(n + 4) {
    transform: translateY(-30px) scale(0.9);
    pointer-events: none;
  }
`;
