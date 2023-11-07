import { styled } from 'styled-components';
import { Z_INDEX } from '../../constants/magicNumber';

interface TabBarProps {
  $isCustomerMode: boolean;
}

export const TabBarContainer = styled.div<TabBarProps>`
  position: fixed;
  display: ${({ $isCustomerMode: $isShow }) => ($isShow ? 'flex' : 'none')};
  justify-content: space-around;
  align-items: center;
  bottom: 0;
  width: 100%;
  max-width: ${({ $isCustomerMode: $isShow }) => ($isShow ? '450px' : '768px')};
  height: 80px;
  border-radius: 8px 8px 0 0;
  box-shadow: 0px -4px 8px 0 rgba(0, 0, 0, 0.1);
  background: white;
  z-index: ${Z_INDEX.above};

  @media screen and (max-width: 768px) {
    display: flex;
  }
`;

export const TapBarItem = styled.div<{ $isSelected: boolean }>`
  & > a {
    display: flex;
    width: 70px;
    flex-direction: column;
    align-items: center;
    color: ${({ $isSelected }) => ($isSelected ? 'black' : '#999')};

    font-size: 14px;
    text-decoration: none;
    transform: ${({ $isSelected }) => ($isSelected ? 'Scale(1.05)' : null)};
    transition: all 0.2s ease-in-out;
    gap: 4px;
  }
`;
