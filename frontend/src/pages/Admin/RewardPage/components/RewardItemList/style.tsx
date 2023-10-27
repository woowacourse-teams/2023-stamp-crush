import styled from 'styled-components';

export const RewardItemListContainer = styled.ul`
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 20px;
`;

export const RewardItem = styled.li`
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: space-between;

  width: 90%;

  font-weight: 600;
  button {
    width: 80px;
    height: 32px;
    padding: 5px;
  }
`;

export const RewardName = styled.span`
  display: flex;
  align-items: center;
  width: 100%;
  height: 20px;
  font-size: 18px;

  padding: 5px;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  &:hover {
    overflow: visible;
    white-space: normal;
  }
`;
