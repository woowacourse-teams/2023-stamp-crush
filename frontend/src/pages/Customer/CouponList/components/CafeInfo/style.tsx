import { styled } from 'styled-components';

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
