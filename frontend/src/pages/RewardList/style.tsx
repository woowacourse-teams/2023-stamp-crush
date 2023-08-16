import { styled } from 'styled-components';

export const RewardContainer = styled.li`
  list-style-type: none;
`;

export const RewardWrapper = styled.ul`
  display: flex;
  align-items: center;
  justify-content: space-between;
  height: 55px;
  border-bottom: 1px solid #eee;
  padding: 0 25px;

  cursor: pointer;
`;

export const CafeName = styled.span`
  font-weight: 500;
`;

export const RewardName = styled.span`
  width: 100px;
  overflow: hidden;
  text-overflow: ellipsis;
  font-weight: 600;
  color: ${({ theme }) => theme.colors.text};
`;
