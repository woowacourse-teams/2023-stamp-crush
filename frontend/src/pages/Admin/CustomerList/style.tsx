import { styled } from 'styled-components';

export const CustomerContainer = styled.li`
  display: flex;
  flex-direction: column;
  margin-top: 40px;
  width: 70%;
`;

export const CustomerBox = styled.ul`
  display: flex;
  justify-content: space-between;
  height: 90px;
  padding: 15px;

  margin-bottom: 15px;
  border-radius: 10px;
  background-color: ${({ theme }) => theme.colors.gray100};
  box-shadow: 0 10px 10px -3px ${({ theme }) => theme.colors.gray300};

  &:first-of-type {
    margin-top: 20px;
  }
`;

export const RightInfo = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: flex-end;
  align-items: flex-end;

  & > span {
    font-size: 14px;
    color: #888888;
    line-height: 16px;
  }
`;

export const LeftInfo = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: space-between;

  & > span {
    font-size: 14px;
    color: #888888;
    line-height: 16px;
  }
`;

export const Badge = styled.div<{ $isRegistered: boolean }>`
  width: 40px;
  height: 18px;
  border-radius: 4px;
  line-height: 18px;
  text-align: center;
  font-size: 12px;
  color: black;

  background: ${({ $isRegistered, theme }) => ($isRegistered ? 'pink' : theme.colors.gray300)};
`;

export const Name = styled.div`
  display: flex;
  align-items: center;
  gap: 10px;

  & > h1 {
    font-size: 20px;
    font-weight: 500;
  }
`;

export const Container = styled.div`
  display: flex;
  align-self: flex-end;
  align-items: center;
  gap: 20px;
  margin-top: 30px;
`;
