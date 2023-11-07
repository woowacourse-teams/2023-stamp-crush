import { styled } from 'styled-components';

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

export const CouponListContainer = styled.div<{
  $isOn: boolean;
}>`
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 350px;
  min-height: 500px;
  padding: 10px 0 200px 0;
  transition: all 0.1s;
  margin: 0 auto;
  overflow: ${({ $isOn }) => ($isOn ? 'hidden' : 'scroll')};

  &::-webkit-scrollbar {
    display: none;
  }
`;

export const ToggleContainer = styled.section`
  display: flex;
  justify-content: flex-end;
  align-items: center;
  padding: 2rem 40px;
`;

export const ToggleName = styled.span<{ $isOn: boolean }>`
  font-size: 13px;
  margin-right: 8px;
  margin-left: 20px;
  opacity: ${({ $isOn }) => ($isOn ? '100%' : '50%')};
  transition: opacity 0.2s ease-in;
`;
