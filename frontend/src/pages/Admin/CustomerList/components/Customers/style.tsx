import { styled } from 'styled-components';
import { skeletonLoading } from '../../../../../style/keyframes';

export const Container = styled.ul`
  display: flex;
  flex-direction: column;
  overflow: scroll;
  max-height: 550px;
`;

export const Badge = styled.div<{ $isRegistered: boolean }>`
  width: 40px;
  height: 18px;
  border-radius: 4px;
  line-height: 18px;
  text-align: center;
  font-size: 12px;
  color: black;
  background: ${({ $isRegistered, theme }) =>
    $isRegistered ? theme.colors.point : theme.colors.gray300};
`;

export const NameContainer = styled.div`
  display: flex;
  align-items: center;
  gap: 10px;
  color: #222;

  @media screen and (max-width: 450px) {
    width: 100px;
  }
`;

export const RightInfo = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: flex-end;
  align-items: flex-end;
  margin-top: 10px;
`;

export const InfoContainer = styled.span`
  font-size: 14px;
  color: ${({ theme }) => theme.colors.text};
  line-height: 16px;

  @media screen and (max-width: 450px) {
    font-size: 13px;
  }
`;

export const CustomerBox = styled.li`
  display: flex;
  justify-content: space-between;
  min-height: 90px;
  padding: 15px;

  margin: 0 20px 15px 20px;
  border-radius: 10px;
  background-color: ${({ theme }) => theme.colors.gray100};
  box-shadow: 0 10px 10px -3px ${({ theme }) => theme.colors.gray300};

  &:first-of-type {
    margin-top: 20px;
  }

  @media screen and (max-width: 450px) {
    margin: 8px 0;
  }
`;

export const LeftInfo = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: space-between;
`;

export const Name = styled.h1`
  font-size: 20px;
  font-weight: 500;

  @media screen and (max-width: 450px) {
    font-size: 16px;
  }
`;

export const EmptyCustomers = styled.p`
  display: flex;
  flex-direction: column;
  margin: 180px 30px;
  line-height: 24px;
  text-align: center;
  color: gray;

  & > span {
    color: #222;
    font-size: 18px;
    font-weight: 900;
    margin-bottom: 10px;
  }
`;

export const Skeleton = styled.div`
  width: 95%;
  height: 90px;
  background: linear-gradient(90deg, #f0f0f0 25%, #e0e0e0 50%, #f0f0f0 75%);
  background-size: 200% 100%;
  animation: ${skeletonLoading} 1.5s infinite;
  margin: 20px 20px 0px 20px;
  border-radius: 10px;
  box-shadow: 0 10px 10px -3px ${({ theme }) => theme.colors.gray300};

  :first-of-type {
    margin-top: 20px;
  }
`;

export const ErrorBox = styled.p`
  display: flex;
  flex-direction: column;
  align-items: center;
  margin: 180px 30px;
  line-height: 24px;
  text-align: center;
  color: gray;

  & > span {
    color: #222;
    font-size: 24px;
    font-weight: 900;
    margin-bottom: 15px;
  }
`;
