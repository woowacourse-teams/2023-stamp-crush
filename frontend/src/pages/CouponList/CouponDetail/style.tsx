import styled from 'styled-components';

export const CouponDetailContainer = styled.section<{ $isDetail: boolean }>`
  opacity: ${({ $isDetail }) => ($isDetail ? '1' : '0')};

  position: absolute;
  top: 0;
  right: 0;
  width: 100vw;
  height: 100vh;
  transform: translateX(${({ $isDetail }) => ($isDetail ? '0' : '500px')});
  transition: all 0.4s ease-in-out;

  background: white;

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
// 텍스트 애니메이션 삽입 ㄱ

export const OverviewContainer = styled.div`
  display: flex;
  flex-direction: column;
  position: absolute;
  align-items: center;
  width: 100%;
  height: 130px;
  top: 250px;
  padding: 0 10px;
  gap: 10px;
  word-break: break-all;
  overflow: hidden;
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
  bottom: 30%;
  left: 50px;
  width: 300px;
  height: 100px;
  gap: 20px;

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
