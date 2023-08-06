import styled, { css } from 'styled-components';
import { popup } from '../../../style/keyframes';

export const CouponDetailContainer = styled.section<{ $isDetail: boolean }>`
  display: none;
  position: absolute;
  top: 0;
  left: 0;
  width: 100vw;
  height: 100vh;
  background: white;
  max-width: 450px;
  transform: translateY(100%);

  ${({ $isDetail }) =>
    $isDetail &&
    css`
      display: flex;
      animation: ${popup} 0.4s;
      transform: translateY(0);
    `}

  h1 {
    white-space: pre-line;
  }

  > :nth-child(2) {
    position: absolute;
    top: 68px;
    left: 50%;
    transform: translateX(-50%);
    z-index: 999;
  }
`;

export const CouponNotification = styled.p`
  position: absolute;
  top: 100px;
  left: 0px;
`;

export const OverviewContainer = styled.div`
  display: flex;
  flex-direction: column;
  position: absolute;
  align-items: center;
  width: 100%;
  height: 130px;
  top: 260px;
  padding: 0 30px;
  gap: 10px;
  line-height: 24px;
  word-break: break-all;
  overflow: hidden;

  :nth-child(1) {
    padding: 5px 20px;
    border-bottom: 2px solid black;
  }
`;

export const CafeImage = styled.img`
  width: 100%;
  height: 100%;
  opacity: 0.45;
  object-fit: cover;
`;

export const ContentContainer = styled.div`
  display: flex;
  flex-direction: column;
  position: absolute;
  bottom: 15%;
  left: 50px;
  width: 300px;
  gap: 10px;

  :nth-child(n) {
    display: flex;
    flex-direction: row;
    align-items: center;
    justify-content: flex-start;
    gap: 10px;
  }
`;

export const CloseButton = styled.button`
  position: absolute;
  top: 15px;
  left: 15px;
  width: 24px;
  height: 24px;
  color: black;
  background: transparent;
`;
